package com.swc.orangeBook.data.align.domain.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author Wilson
 * @Description: 更新
 * @date 2024/11/17 17:52
 */
public interface UpdateMapper {

    /**
     * 更新 t_user_count 计数表总关注数
     * @param userId
     * @return
     */
    int updateUserFollowingTotalByUserId(@Param("userId") long userId,
                                         @Param("followingTotal") int followingTotal);

    /**
     * 更新 t_user_count 计数表总粉丝数
     * @param userId
     * @return
     */
    int updateUserFansTotalByUserId(@Param("userId") long userId,
                                         @Param("fansTotal") int fansTotal);

    /**
     * 更新 t_note_count 计数表笔记点赞数
     */
    int updateNoteLikeTotalByNoteId(@Param("noteId") long noteId,
                                    @Param("noteLikeTotal") int noteLikeTotal);

    /**
     * 更新 t_note_count 计数表笔记收藏数
     */
    int updateNoteCollectTotalByNoteId(@Param("noteId") long noteId,
                                    @Param("noteCollectTotal") int noteCollectTotal);

    /**
     * 更新 t_user_count 计数表获得收藏总数
     * @param userId
     * @return
     */
    int updateUserCollectTotalByUserId(@Param("userId") Long userId,
                                       @Param("userCollectTotal") int userCollectTotal);

    /**
     * 更新 t_user_count 计数表获得点赞总数
     * @param userId
     * @return
     */
    int updateUserLikeTotalByUserId(@Param("userId") Long userId,
                                       @Param("userLikeTotal") int userLikeTotal);
}

