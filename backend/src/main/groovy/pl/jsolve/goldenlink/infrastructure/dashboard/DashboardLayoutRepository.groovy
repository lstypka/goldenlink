package pl.jsolve.goldenlink.infrastructure.dashboard
import org.springframework.data.mongodb.repository.MongoRepository

interface DashboardLayoutRepository extends MongoRepository<DashboardLayoutEntity, String> {}
