local issuedCountKey = KEYS[1]
local issuedUsersKey = KEYS[2]

local userId = ARGV[1]

if redis.call('SREM', issuedUsersKey, userId) == 1 then
    redis.call('DECR', issuedCountKey)
end

return 1
