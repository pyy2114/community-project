package com.project.community.entity

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(

    @Column(name = "login_id", unique = true, nullable = false)
    var loginId: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "nickname", unique = true, nullable = false)
    var nickname: String,

    @Column(name = "role_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    var role: RoleType,

    //연관관계 설정
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    var boards: MutableList<Board> = mutableListOf()

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null
}