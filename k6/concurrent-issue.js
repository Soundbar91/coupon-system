import http from 'k6/http';
import { check } from 'k6';
import { Counter } from 'k6/metrics';
import exec from 'k6/execution';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const COUPON_ID = __ENV.COUPON_ID || '1';
const CONCURRENT_USERS = Number(__ENV.N || '1000');
const EXPECTED_SUCCESS = Number(__ENV.EXPECTED_SUCCESS || '100');
const USER_ID_START = Number(__ENV.USER_ID_START || '100000');

const issueSuccess = new Counter('issue_success');
const outOfStock = new Counter('issue_out_of_stock');
const duplicateIssue = new Counter('issue_duplicate');
const unexpectedFailure = new Counter('issue_unexpected_failure');

export const options = {
  scenarios: {
    concurrent_issue: {
      executor: 'shared-iterations',
      vus: CONCURRENT_USERS,
      iterations: CONCURRENT_USERS,
      maxDuration: '1m',
    },
  },
  thresholds: {
    issue_success: [`count==${EXPECTED_SUCCESS}`],
    issue_unexpected_failure: ['count==0'],
    checks: ['rate==1'],
  },
};

export default function () {
  const userId = USER_ID_START + exec.scenario.iterationInTest;
  const url = `${BASE_URL}/api/coupons/${COUPON_ID}/issue`;
  const payload = JSON.stringify({ userId });
  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = http.post(url, payload, params);
  const body = parseJson(response);

  const success = response.status === 200 && body.success === true;
  const conflict = response.status === 409 && body.success === false;
  const message = conflict ? body.data : '';

  if (success) {
    issueSuccess.add(1);
  } else if (message === '쿠폰 재고가 소진되었습니다.') {
    outOfStock.add(1);
  } else if (message === '이미 발급받은 쿠폰입니다.') {
    duplicateIssue.add(1);
  } else {
    unexpectedFailure.add(1);
  }

  check(response, {
    '발급 성공 또는 재고 소진 응답만 반환': () => success || message === '쿠폰 재고가 소진되었습니다.',
  });
}

function parseJson(response) {
  try {
    return response.json();
  } catch (error) {
    return {};
  }
}
