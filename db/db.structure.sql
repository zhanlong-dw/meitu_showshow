--用户信息表
create table MeituUser(
       userId  varchar2(50) primary key,--用户编号
       userName varchar2(50) unique not null ,--用户名
       userPwd  varchar2(50) not null,--用户密码
       userRank  int ,--用户级别，
       userTegral number(6),--用户积分
       userRegisterTime date--注册时间
       
);

create sequence seq_MeituUser_userId start with 0001 increment by 1;

--图片信息表
create table MeituPicture(
       userId varchar2(50),--用户编号
       picId  varchar2(500) primary key,--图片编号
       picName varchar2(50) unique not null, --图片名称
       picPicture  blob ,--图片
       picComent  varchar2(500),--图片备注
       picSavetime date,--修改日期
       picDistinguish varchar2(50),--图片的分辨率
       picWidth number(4),--图片宽
       picHeight number(4),--图片高
       picForm  varchar2(50) --图片格式   
);


create sequence sqe_MeituPicture_userId start with 0001 increment by 1;
 alter table MeituPicture
       add constraint FK_MeituPicture_MeituUser foreign key (userId) references MeituUser (userId);
      
commit; 

-------------------------------------------------
select * from Meituuser;