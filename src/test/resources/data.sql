INSERT INTO `tbl_prefecture` (`prefecture_id`, `prefecture_kana`, `prefecture`, `prefecture_code`) VALUES ('2288', 'ﾎｯｶｲﾄﾞｳ', '北海道', '01');

INSERT INTO `tbl_prefecture` (`prefecture_id`, `prefecture_kana`, `prefecture`, `prefecture_code`) VALUES ('2894', 'ﾎｯｶｲﾄﾞｳ', '北海道2', '09');

INSERT INTO `tbl_city` (`city_id`, `code`, `city_kana`, `city`, `prefecture_id`) VALUES ('413134', '01102', 'ｻｯﾎﾟﾛｼｷﾀ', '札幌市北区', '2288');

INSERT INTO `tbl_city` (`city_id`, `code`, `city_kana`, `city`, `prefecture_id`) VALUES ('413135', '01103', 'ｱﾏｸﾞﾝｶﾆｴﾁｮｳupdate2', '海部郡蟹江町update2', '2288');

INSERT INTO `tbl_old_post` (`old_post_id`, `old_post_code`) VALUES ('16383', '001');

INSERT INTO `tbl_post` (`post_id`, `post_code`, `update_show`, `change_reason`, `multi_area`) VALUES ('393221', '0010000', '0', '0', '0');

INSERT INTO `tbl_area` (`area_id`, `area_kana`, `area`, `city_id`, `chome_area`, `koaza_area`, `multi_post_area`, `post_id`, `old_post_id`) VALUES ('8', 'ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱ', '以下に掲載がない場合', '413134', '1', '0', '0', '393221', '16383');
