package com.dongguk.cse.naemansan.domain;

import com.dongguk.cse.naemansan.domain.type.ImageUseType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "images")
@DynamicUpdate
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "use_user", nullable = true)
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "use_shop", nullable = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "uuid_name", nullable = false)
    private String uuidName;

    @Column(name = "type", nullable = false)
    private String type;

    @Builder
    public Image(Object useObject, ImageUseType imageUseType, String originName, String uuidName, String type) {
        switch (imageUseType) {
            case USER -> {
                this.user = (User) useObject;
                this.shop = null;
            }
            case SHOP -> {
                this.shop = (Shop) useObject;
                this.user = null;
            }
        }
        this.originName = originName;
        this.uuidName = uuidName;
        this.type = type;
    }

    public void updateImage(String originName, String uuidName, String type) {
        this.originName = originName;
        this.uuidName = uuidName;
        this.type = type;
    }
}
