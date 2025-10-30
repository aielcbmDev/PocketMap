package com.charly.database.utils

import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationEntity
import com.charly.domain.model.Group
import com.charly.domain.model.Location
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MappersTest {

    @Test
    fun `Verify that GroupEntity mapToGroup returns a Group`() {
        // GIVEN
        val groupEntity = GroupEntity(
            id = 23,
            name = "name23"
        )

        // WHEN
        val group = groupEntity.mapToGroup()

        // THEN
        assertIs<Group>(group)
        assertEquals(groupEntity.id, group.id)
        assertEquals(groupEntity.name, group.name)
    }

    @Test
    fun `Verify that a list of GroupEntity mapToGroupList returns a list of Group`() {
        // GIVEN
        val groupEntityList = listOf(
            GroupEntity(
                id = 23,
                name = "name23"
            ),
            GroupEntity(
                id = 5,
                name = "something"
            )
        )

        // WHEN
        val groupList = groupEntityList.mapToGroupList()

        // THEN
        assertEquals(2, groupList.size)
        val groupEntity0 = groupEntityList[0]
        val group0 = groupList[0]
        assertIs<Group>(group0)
        assertEquals(groupEntity0.id, group0.id)
        assertEquals(groupEntity0.name, group0.name)
        val groupEntity1 = groupEntityList[1]
        val group1 = groupList[1]
        assertIs<Group>(group1)
        assertEquals(groupEntity1.id, group1.id)
        assertEquals(groupEntity1.name, group1.name)
    }

    @Test
    fun `Verify that a String mapToGroupEntity returns a GroupEntity`() {
        // GIVEN
        val groupName = "Whatever name"

        // WHEN
        val groupEntity = groupName.mapToGroupEntity()

        // THEN
        assertIs<GroupEntity>(groupEntity)
        assertEquals(0, groupEntity.id)
        assertEquals(groupName, groupEntity.name)
    }

    @Test
    fun `Verify that a Group mapToGroupEntity returns a GroupEntity`() {
        // GIVEN
        val group = Group(
            id = 14,
            name = "whfsdjgdf"
        )

        // WHEN
        val groupEntity = group.mapToGroupEntity()

        // THEN
        assertIs<GroupEntity>(groupEntity)
        assertEquals(group.id, groupEntity.id)
        assertEquals(group.name, groupEntity.name)
    }

    @Test
    fun `Verify that a list of Group mapToGroupEntityList returns a list of GroupEntity`() {
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
        val groupEntityList = groupList.mapToGroupEntityList()

        // THEN
        assertEquals(2, groupList.size)
        val group0 = groupList[0]
        val groupEntity0 = groupEntityList[0]
        assertIs<GroupEntity>(groupEntity0)
        assertEquals(group0.id, groupEntity0.id)
        assertEquals(group0.name, groupEntity0.name)
        val group1 = groupList[1]
        val groupEntity1 = groupEntityList[1]
        assertIs<GroupEntity>(groupEntity1)
        assertEquals(group1.id, groupEntity1.id)
        assertEquals(group1.name, groupEntity1.name)
    }

    @Test
    fun `Verify that LocationEntity mapToLocation returns a Location`() {
        // GIVEN
        val locationEntity = LocationEntity(
            id = 23,
            title = "title23",
            description = "description23",
            latitude = 1.0,
            longitude = 2.0
        )

        // WHEN
        val location = locationEntity.mapToLocation()

        // THEN
        assertIs<Location>(location)
        assertEquals(locationEntity.id, location.id)
        assertEquals(locationEntity.title, location.title)
        assertEquals(locationEntity.description, location.description)
        assertEquals(locationEntity.latitude, location.latitude)
        assertEquals(locationEntity.longitude, location.longitude)
    }

    @Test
    fun `Verify that a list of LocationEntity mapToLocationList returns a list of Location`() {
        // GIVEN
        val locationEntityList = listOf(
            LocationEntity(
                id = 55,
                title = "title23",
                description = "description23",
                latitude = 1.0,
                longitude = 2.0
            ),
            LocationEntity(
                id = 5,
                title = "something",
                description = "something",
                latitude = 3.0,
                longitude = 4.0
            )
        )

        // WHEN
        val locationList = locationEntityList.mapToLocationList()

        // THEN
        assertEquals(2, locationList.size)
        val locationEntity0 = locationEntityList[0]
        val location0 = locationList[0]
        assertIs<Location>(location0)
        assertEquals(locationEntity0.id, location0.id)
        assertEquals(locationEntity0.title, location0.title)
        assertEquals(locationEntity0.description, location0.description)
        assertEquals(locationEntity0.latitude, location0.latitude)
        assertEquals(locationEntity0.longitude, location0.longitude)
        val locationEntity1 = locationEntityList[1]
        val location1 = locationList[1]
        assertIs<Location>(location1)
        assertEquals(locationEntity1.id, location1.id)
        assertEquals(locationEntity1.title, location1.title)
        assertEquals(locationEntity1.description, location1.description)
        assertEquals(locationEntity1.latitude, location1.latitude)
        assertEquals(locationEntity1.longitude, location1.longitude)
    }

    @Test
    fun `Verify that Location mapToLocationEntity returns a LocationEntity`() {
        // GIVEN
        val location = Location(
            id = 23,
            title = "title23",
            description = "description23",
            latitude = 1.0,
            longitude = 2.0
        )

        // WHEN
        val locationEntity = location.mapToLocationEntity()

        // THEN
        assertIs<LocationEntity>(locationEntity)
        assertEquals(location.id, locationEntity.id)
        assertEquals(location.title, locationEntity.title)
        assertEquals(location.description, locationEntity.description)
        assertEquals(location.latitude, locationEntity.latitude)
        assertEquals(location.longitude, locationEntity.longitude)
    }

    @Test
    fun `Verify that a list of Location mapToLocationEntityList returns a list of LocationEntity`() {
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
        val locationEntityList = locationList.mapToLocationEntityList()

        // THEN
        assertEquals(2, locationEntityList.size)
        val location0 = locationList[0]
        val locationEntity0 = locationEntityList[0]
        assertIs<LocationEntity>(locationEntity0)
        assertEquals(location0.id, locationEntity0.id)
        assertEquals(location0.title, locationEntity0.title)
        assertEquals(location0.description, locationEntity0.description)
        assertEquals(location0.latitude, locationEntity0.latitude)
        assertEquals(location0.longitude, locationEntity0.longitude)
        val location1 = locationList[1]
        val locationEntity1 = locationEntityList[1]
        assertIs<LocationEntity>(locationEntity1)
        assertEquals(location1.id, locationEntity1.id)
        assertEquals(location1.title, locationEntity1.title)
        assertEquals(location1.description, locationEntity1.description)
        assertEquals(location1.latitude, locationEntity1.latitude)
        assertEquals(location1.longitude, locationEntity1.longitude)
    }
}
