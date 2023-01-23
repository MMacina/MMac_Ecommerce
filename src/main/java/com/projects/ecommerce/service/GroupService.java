package com.projects.ecommerce.service;

import com.projects.ecommerce.domain.Group;
import com.projects.ecommerce.exception.GroupNotFoundException;
import com.projects.ecommerce.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    public Group getGroupById(Long id) throws GroupNotFoundException {
        return groupRepository.findById(id).orElseThrow(() -> new GroupNotFoundException(
                "Group update not found id: " + id));
    }

    public Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
    public Group updateGroup(final Group group, final Long id) throws GroupNotFoundException {
        Optional<Group> groupEntity = groupRepository.findById(id);
        Group groupForUpdate = groupEntity.orElseThrow(() -> new GroupNotFoundException(
                "Group update not found id: " + id));
        groupForUpdate.setName(group.getName());
        return groupRepository.save(groupForUpdate);
    }
}