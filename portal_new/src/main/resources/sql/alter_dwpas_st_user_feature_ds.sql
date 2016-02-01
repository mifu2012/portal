#用户分布加销量：
alter table dwpas_st_user_feature_ds 
add sales_volume VARCHAR(50); 


update dwpas_st_user_feature_ds a set a.sales_volume=a.product_id-20;