package com.dongguk.cse.naemansan.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="course_tags")
public class CourseTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "course_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EnrollmentCourse enrollmentCourse;

    @JoinColumn(name = "tag_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @Builder
    public CourseTag(EnrollmentCourse enrollmentCourse, Tag tag) {
        this.enrollmentCourse = enrollmentCourse;
        this.tag = tag;
    }
}
