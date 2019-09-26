package com.training.api.entitys.fixtures;

import com.training.api.entitys.TblPost;

/**
 * Fixtures for {@link TblPost}
 *
 */
public class TblPostFixtures {

    public static TblPost createPost() {
        TblPost tblPost = new TblPost("0010000",0,0,0);

        return tblPost;
    }
}
