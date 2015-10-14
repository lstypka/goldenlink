package pl.jsolve.goldenlink.infrastructure.dashboard

import org.springframework.data.mongodb.repository.MongoRepository

interface DashboardTileRepository extends MongoRepository<DashboardTileEntity, String>{

}