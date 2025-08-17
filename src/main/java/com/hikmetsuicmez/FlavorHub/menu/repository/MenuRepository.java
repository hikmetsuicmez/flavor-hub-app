package com.hikmetsuicmez.FlavorHub.menu.repository;

import com.hikmetsuicmez.FlavorHub.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

}
