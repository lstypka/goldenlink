package pl.jsolve.goldenlink.service.friend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.api.friend.Friend
import pl.jsolve.goldenlink.infrastructure.friend.FriendRepository
import pl.jsolve.goldenlink.service.AutoMap

@Service
class FriendService {

    @Autowired
    FriendRepository friendRepository

    @AutoMap(result = Friend)
    def retrieveFriends() {
        friendRepository.findAll()
    }
}
