local issuedCountKey = KEYS[1]
local issuedUsersKey = KEYS[2]

local userId = ARGV[1]
local totalQuantity = tonumber(ARGV[2])

if redis.call('SISMEMBER', issuedUsersKey, userId) == 1 then
    return -1
end

local issuedCount = tonumber(redis.call('GET', issuedCountKey) or '0')

if issuedCount >= totalQuantity then
    return -2
end

redis.call('SADD', issuedUsersKey, userId)
redis.call('INCR', issuedCountKey)

return 1
