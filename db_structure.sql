--用户信息表
create table MeituUser(
       userId  int primary key,--用户编号
       userName varchar2(50) unique not null ,--用户名
       userPwd  varchar2(50) not null,--用户密码
       userRank  int ,--用户级别，
       userTegral int,--用户积分
       userRegisterTime date--注册时间
       
);

create sequence seq_MeituUser_userId start with 1001 increment by 1;

--图片信息表(上传记录表)
create table MeituPicture(
       userId int,--用户编号
       picId  int primary key,--图片编号
       picName varchar2(50) unique not null, --图片名称
       picPicture  blob ,--图片
       picComent  varchar2(300),--图片备注
       picSavetime date,--保存日期
       picResolution varchar2(50),--图片的分辨率
       PicSize varchar2(20),--图片大小
       picFormat  varchar2(20), --图片格式   
       picState varchar2(4) default '否'--图片状态（是否共享）
);


create sequence seq_MeituPicture_picId start with 2001 increment by 1;
 alter table MeituPicture
       add constraint FK_MeituPicture_MeituUser foreign key (userId) references MeituUser (userId);
 
--下载记录信息表
create table DownloadInfo(
       downId int primary key,--下载编号
       userId  int,--下载用户的编号
       picId  int ,--图片编号
       picName varchar2(50), --图片名称
       picComent  varchar2(300),--图片备注
       picDowntime date,--下载日期
       picResolution varchar2(50),--图片的分辨率
       picSize varchar2(20),--图片大小
       picFormat  varchar2(50) --图片格式
  
);
insert into DownloadInfo values(seq_DownloadInfo_downId.nextval,userId,picId,picName,picComent,picDowntime,picResolution,picSize,picFormat);
alter table DownloadInfo
       add constraint FK_DownloadInfo_MeituPicture foreign key (picId) references MeituPicture (picId);
create sequence seq_DownloadInfo_downId start with 3001 increment by 1;       
commit;     
       
drop table  MeituPicture;--删除图片信息表
drop table MeituUser;--删除用户信息
drop table DownloadInfo;--删除下载记录信息表     

select * from MeituUser;
select * from  MeituPicture;
select * from DownloadInfo;
