insert into MeituPicture values(1001,1,'ͼ1',null,'dasdsa',sysdate,'800x900','2M','jpg','是');
insert into MeituPicture values(1001,2,'ͼ2',null,'dasdsa',sysdate,'800x900','2M','jpg','是');
insert into MeituPicture values(1001,3,'ͼ3',null,'dasdsa',sysdate,'800x900','2M','jpg','是');
insert into MeituPicture values(1001,4,'ͼ4',null,'dasdsa',sysdate,'800x900','2M','jpg','是');
insert into MeituPicture values(1001,5,'ͼ5',null,'dasdsa',sysdate,'800x900','2M','jpg','是');
insert into MeituPicture values(1001,6,'ͼ6',null,'dasdsa',sysdate,'800x900','2M','jpg','是');

insert into downloadinfo values(3001,1001,1,'ͼ1','dasdsa',sysdate,'800x900','2M','jpg');
insert into downloadinfo values(3002,1002,2,'ͼ2','dasdsa',sysdate,'800x900','2M','jpg');
insert into downloadinfo values(3003,1003,3,'ͼ3','dasdsa',sysdate,'800x900','2M','jpg');
insert into downloadinfo values(3004,1004,4,'ͼ4','dasdsa',sysdate,'800x900','2M','jpg');
insert into downloadinfo values(3005,1005,5,'ͼ5','dasdsa',sysdate,'800x900','2M','jpg');
insert into downloadinfo values(3006,1006,6,'ͼ6','dasdsa',sysdate,'800x900','2M','jpg');
select * from MeituUser;
select * from MeituPicture;
select * from downloadinfo;
select count(*) from MeituPicture
alter table downloadinfo drop constraint FK_DownloadInfo_MeituUser
commit;
select userName from MeituUser u inner join MeituPicture p on p.userId=u.userId where picId=6;
