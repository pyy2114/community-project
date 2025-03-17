package com.project.community.entity

import jakarta.persistence.*

@Entity
@Table(name = "board")
class Board(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    var content: String,

    @Column(name = "views", nullable = false)
    var views: Long = 0,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category

) : BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    val id: Long? = null

    fun updateBoard(title: String, content: String){
        this.title = title
        this.content = content
    }

    fun increaseViews(){
        this.views += 1
    }

    fun changeCategory(category: Category){
        this.category = category
    }
}