package pl.jsolve.goldenlink.infrastructure.friend

import org.springframework.data.mongodb.repository.MongoRepository

interface FriendRepository extends MongoRepository<FriendEntity, String> {}