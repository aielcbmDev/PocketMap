package charly.baquero.pocketmap.ui.utils

import charly.baquero.pocketmap.ui.model.GroupModel
import charly.baquero.pocketmap.ui.model.LocationModel
import com.charly.domain.model.database.Group
import com.charly.domain.model.database.Location
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MappersTest {

    @Test
    fun `Verify that GroupModel mapToGroup returns a Group`() {
        // GIVEN
        val groupModel = GroupModel(
            id = 23,
            name = "name23"
        )

        // WHEN
        val group = groupModel.mapToGroup()

        // THEN
        assertIs<Group>(group)
        assertEquals(groupModel.id, group.id)
        assertEquals(groupModel.name, group.name)
    }

    @Test
    fun `Verify that a list of GroupModel mapToGroupList returns a list of Group`() {
        // GIVEN
        val groupModelList = listOf(
            GroupModel(
                id = 23,
                name = "name23"
            ),
            GroupModel(
                id = 5,
                name = "something"
            )
        )

        // WHEN
        val groupList = groupModelList.mapToGroupList()

        // THEN
        assertEquals(2, groupList.size)
        val groupModel0 = groupModelList[0]
        val group0 = groupList[0]
        assertIs<Group>(group0)
        assertEquals(groupModel0.id, group0.id)
        assertEquals(groupModel0.name, group0.name)
        val groupModel1 = groupModelList[1]
        val group1 = groupList[1]
        assertIs<Group>(group1)
        assertEquals(groupModel1.id, group1.id)
        assertEquals(groupModel1.name, group1.name)
    }

    @Test
    fun `Verify that a Group mapToGroupModel returns a GroupModel`() {
        // GIVEN
        val group = Group(
            id = 14,
            name = "whfsdjgdf"
        )

        // WHEN
        val groupModel = group.mapToGroupModel()

        // THEN
        assertIs<GroupModel>(groupModel)
        assertEquals(group.id, groupModel.id)
        assertEquals(group.name, groupModel.name)
    }

    @Test
    fun `Verify that a list of Group mapToGroupModelList returns a list of GroupModel`() {
        // GIVEN
        val groupList = listOf(
            Group(
                id = 23,
                name = "name23"
            ),
            Group(
                id = 5,
                name = "something"
            )
        )

        // WHEN
        val groupModelList = groupList.mapToGroupModelList()

        // THEN
        assertEquals(2, groupList.size)
        val group0 = groupList[0]
        val groupModel0 = groupModelList[0]
        assertIs<GroupModel>(groupModel0)
        assertEquals(group0.id, groupModel0.id)
        assertEquals(group0.name, groupModel0.name)
        val group1 = groupList[1]
        val groupModel1 = groupModelList[1]
        assertIs<GroupModel>(groupModel1)
        assertEquals(group1.id, groupModel1.id)
        assertEquals(group1.name, groupModel1.name)
    }

    @Test
    fun `Verify that LocationModel mapToLocation returns a Location`() {
        // GIVEN
        val locationModel = LocationModel(
            id = 23,
            title = "title23",
            description = "description23",
            latitude = 1.0,
            longitude = 2.0
        )

        // WHEN
        val location = locationModel.mapToLocation()

        // THEN
        assertIs<Location>(location)
        assertEquals(locationModel.id, location.id)
        assertEquals(locationModel.title, location.title)
        assertEquals(locationModel.description, location.description)
        assertEquals(locationModel.latitude, location.latitude)
        assertEquals(locationModel.longitude, location.longitude)
    }

    @Test
    fun `Verify that a list of LocationModel mapToLocationList returns a list of Location`() {
        // GIVEN
        val locationModelList = listOf(
            LocationModel(
                id = 55,
                title = "title23",
                description = "description23",
                latitude = 1.0,
                longitude = 2.0
            ),
            LocationModel(
                id = 5,
                title = "something",
                description = "something",
                latitude = 3.0,
                longitude = 4.0
            )
        )

        // WHEN
        val locationList = locationModelList.mapToLocationList()

        // THEN
        assertEquals(2, locationList.size)
        val locationModel0 = locationModelList[0]
        val location0 = locationList[0]
        assertIs<Location>(location0)
        assertEquals(locationModel0.id, location0.id)
        assertEquals(locationModel0.title, location0.title)
        assertEquals(locationModel0.description, location0.description)
        assertEquals(locationModel0.latitude, location0.latitude)
        assertEquals(locationModel0.longitude, location0.longitude)
        val locationModel1 = locationModelList[1]
        val location1 = locationList[1]
        assertIs<Location>(location1)
        assertEquals(locationModel1.id, location1.id)
        assertEquals(locationModel1.title, location1.title)
        assertEquals(locationModel1.description, location1.description)
        assertEquals(locationModel1.latitude, location1.latitude)
        assertEquals(locationModel1.longitude, location1.longitude)
    }

    @Test
    fun `Verify that Location mapToLocationModel returns a LocationModel`() {
        // GIVEN
        val location = Location(
            id = 23,
            title = "title23",
            description = "description23",
            latitude = 1.0,
            longitude = 2.0
        )

        // WHEN
        val locationModel = location.mapToLocationModel()

        // THEN
        assertIs<LocationModel>(locationModel)
        assertEquals(location.id, locationModel.id)
        assertEquals(location.title, locationModel.title)
        assertEquals(location.description, locationModel.description)
        assertEquals(location.latitude, locationModel.latitude)
        assertEquals(location.longitude, locationModel.longitude)
    }

    @Test
    fun `Verify that a list of Location mapToLocationModelList returns a list of LocationModel`() {
        // GIVEN
        val locationList = listOf(
            Location(
                id = 55,
                title = "title23",
                description = "description23",
                latitude = 1.0,
                longitude = 2.0
            ),
            Location(
                id = 5,
                title = "something",
                description = "something",
                latitude = 3.0,
                longitude = 4.0
            )
        )

        // WHEN
        val locationModelList = locationList.mapToLocationModelList()

        // THEN
        assertEquals(2, locationModelList.size)
        val location0 = locationList[0]
        val locationModel0 = locationModelList[0]
        assertIs<LocationModel>(locationModel0)
        assertEquals(location0.id, locationModel0.id)
        assertEquals(location0.title, locationModel0.title)
        assertEquals(location0.description, locationModel0.description)
        assertEquals(location0.latitude, locationModel0.latitude)
        assertEquals(location0.longitude, locationModel0.longitude)
        val location1 = locationList[1]
        val locationModel1 = locationModelList[1]
        assertIs<LocationModel>(locationModel1)
        assertEquals(location1.id, locationModel1.id)
        assertEquals(location1.title, locationModel1.title)
        assertEquals(location1.description, locationModel1.description)
        assertEquals(location1.latitude, locationModel1.latitude)
        assertEquals(location1.longitude, locationModel1.longitude)
    }
}
