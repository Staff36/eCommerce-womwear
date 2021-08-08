package ru.tronin.msproducts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tronin.msproducts.models.entities.Category;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
}
