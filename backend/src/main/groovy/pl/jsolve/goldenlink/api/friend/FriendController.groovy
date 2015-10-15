package pl.jsolve.goldenlink.api.friend
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.service.friend.FriendService

import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping('/friends')
class FriendController {

    @Autowired
    FriendService friendService

    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    def 'get friends'() {
        friendService.retrieveFriends()
    }
}
