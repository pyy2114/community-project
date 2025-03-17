package com.project.community.entity

import jakarta.persistence.*

@Entity
@Table(name = "category")
class Category(
    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    var boards: MutableList<Board> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    val id: Long? = null
}