--�û���Ϣ��
create table MeituUser(
       userId  int primary key,--�û����
       userName varchar2(50) unique not null ,--�û���
       userPwd  varchar2(50) not null,--�û�����
       userRank  int ,--�û�����
       userTegral int,--�û�����
       userRegisterTime date--ע��ʱ��
       
);

create sequence seq_MeituUser_userId start with 1001 increment by 1;

--ͼƬ��Ϣ��(�ϴ���¼��)
create table MeituPicture(
       userId int,--�û����
       picId  int primary key,--ͼƬ���
       picName varchar2(50) unique not null, --ͼƬ����
       picPicture  blob ,--ͼƬ
       picComent  varchar2(300),--ͼƬ��ע
       picSavetime date,--��������
       picResolution varchar2(50),--ͼƬ�ķֱ���
       PicSize varchar2(20),--ͼƬ��С
       picFormat  varchar2(20), --ͼƬ��ʽ   
       picState varchar2(4) default '��'--ͼƬ״̬���Ƿ���
);


create sequence seq_MeituPicture_picId start with 2001 increment by 1;
 alter table MeituPicture
       add constraint FK_MeituPicture_MeituUser foreign key (userId) references MeituUser (userId);
 
--���ؼ�¼��Ϣ��
create table DownloadInfo(
       downId int primary key,--���ر��
       userId  int,--�����û��ı��
       picId  int ,--ͼƬ���
       picName varchar2(50), --ͼƬ����
       picComent  varchar2(300),--ͼƬ��ע
       picDowntime date,--��������
       picResolution varchar2(50),--ͼƬ�ķֱ���
       picSize varchar2(20),--ͼƬ��С
       picFormat  varchar2(50) --ͼƬ��ʽ
  
);
insert into DownloadInfo values(seq_DownloadInfo_downId.nextval,userId,picId,picName,picComent,picDowntime,picResolution,picSize,picFormat);
alter table DownloadInfo
       add constraint FK_DownloadInfo_MeituPicture foreign key (picId) references MeituPicture (picId);
create sequence seq_DownloadInfo_downId start with 3001 increment by 1;       
commit;     
       
drop table  MeituPicture;--ɾ��ͼƬ��Ϣ��
drop table MeituUser;--ɾ���û���Ϣ
drop table DownloadInfo;--ɾ�����ؼ�¼��Ϣ��     

select * from MeituUser;
select * from  MeituPicture;
select * from DownloadInfo;
