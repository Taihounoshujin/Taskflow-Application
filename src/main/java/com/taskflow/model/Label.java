package com.taskflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * A Label is a tag that can be applied to one or more Cards.
 * Labels are scoped to a Board.
 */
@Entity
@Table(name = "labels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    /** Hex color code, e.g. "#FF5733". */
    @Column(nullable = false, length = 7)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}
