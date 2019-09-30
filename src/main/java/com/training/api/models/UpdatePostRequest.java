package com.training.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.api.entitys.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Request to Update {@link Post}.
 */
public class UpdatePostRequest implements UnaryOperator<Post> {

    @NotNull
    @JsonProperty("update_show")
    @Getter
    @Setter
    private int updateShow;

    @JsonProperty("change_reason")
    @NotNull
    @Getter
    @Setter
    private int changeReason;

    @JsonProperty("multi_area")
    @NotNull
    @Getter
    @Setter
    private int multiArea;


    @Override
    public Post apply(Post tblPost) {
        Optional.ofNullable(getUpdateShow()).ifPresent(tblPost::setUpdateShow);
        Optional.ofNullable(getChangeReason()).ifPresent(tblPost::setChangeReason);
        Optional.ofNullable(getMultiArea()).ifPresent(tblPost::setChangeReason);

        return tblPost;
    }
}
