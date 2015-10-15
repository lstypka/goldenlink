package pl.jsolve.goldenlink.api

import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.infrastructure.friend.FriendEntity
import pl.jsolve.goldenlink.infrastructure.friend.FriendRepository
import pl.jsolve.goldenlink.test.specs.ApiIntegrationSpecification

import static org.apache.http.HttpStatus.SC_OK

class FriendControllerApiTest extends ApiIntegrationSpecification {

    @Autowired
    FriendRepository friendRepository

    def 'Should get friends'() {
        given:
        friendRepository.save([
                new FriendEntity(
                        name: 'Glen Glen',
                        thumbnail: 'Face',
                        sex: 'M',
                        numberOfSharedLinks: 2,
                        isGroup: false
                ),
                new FriendEntity(
                        name: 'Louie Anderson',
                        thumbnail: 'Face',
                        sex: 'M',
                        numberOfSharedLinks: 10,
                        isGroup: false
                )
        ])

        when:
        def result = api.get path: '/friends'

        then:
        result.status == SC_OK
        result.data*.name == ['Glen Glen', 'Louie Anderson']
        result.data*.thumbnail == ['Face', 'Face']
        result.data*.sex == ['M', 'M']
        result.data*.numberOfSharedLinks == [2, 10]
    }
}
