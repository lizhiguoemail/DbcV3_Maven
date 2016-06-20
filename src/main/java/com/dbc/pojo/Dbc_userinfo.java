/**
 * @Project dbc
 * @Title Userinfo.java
 * @Package org.dbc.pojo;
 * @Description 用户属性类
 * @author caihuajun
 * @date 2011-05-31
 * @version v1.0
 * 2011-06-2 caihuajun 修改了一些参数类型，例如sortcode变为Integer型方便排序
 * 2011-08-25 caihuajun  增加了用户对模块多对多的关系，用来适应用户权限的分配
 * 2012-11-26 caihuajun 移除与单位表的关联
 * 2013-04-25 caihuajun 增加usertype字段，区分前台用户与后台管理员用户
 * 2013-09-29 caihuajun 修改底层结果，更改类名为userinfo
 * 2014-06-25 caihuajun 增加了企业相关的字段
 */
package com.dbc.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Userinfo
 * @Description 用户属性类
 * @author caihuajun
 * @date 2011-05-31
 */
public class Dbc_userinfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;  //主键
	
	private String username;//帐号
	
	private String password; //密码
	
	private String nickname; //呢称
	
	private String category; //帐号类别
	
	private String tname;  //真实姓名
	
	private String sex;//性别

	private String birthday; //出生日期 

	private String picture;  //照片

	private String email; //邮件地址

	private String qq;  //QQ
	
	private String telephone; //固定电话
	
	private String mobile; //手机
	
	private String duty; //岗位
	
	private String title; //职称
	
	private String firstvisit; //第一次访问时间
	
	private String previousvisit; //上一次访问时间
	
	private String lastvisit; //最后一次访问时间
	
	private String lastip;//最后一次访问ip
	
	private Integer logincount; //登录次数
	
	private String isstaff; //是否是职员
	
	private String auditstatus; //审核状态  0等待审核 1审核通过 2审核未通过
	
	private String ownsigned; //个性签名
	
	private String ipaddress; //IP地址
	
	private String  useronline; //用户在线状态
	
	private String  macaddress; //mac地址	
	
	private String homeaddress; //家庭地址
	
	private String qusetion; //密码提示问题
	
	private String answerquesetion; //提示答案
	
	private String political_status; //政治面貌
	
	private String education; //学历
	
	private String degree; //学位
	
	private String userfrom; //用户来源
	
	private String positionid; //部门职位ID
	
	private String positionname; //部门职位名称
	
	private String departmentid; //部门id
	
	private String departmentname; //部门名称
	
	private String regdate; //注册时间
	
	private String iswanshan; //信息是否完善
	
	private Double money; //用户余额
	
	private Double integral; //用户积分
	
	private String business_license;  //营业执照
	
	private String organization_code; //组织机构代码证
	
	private String tax_registration_certificate; //税务登记证
	
	private String bank; //银行
	
	private String bank_account; //银行帐号
	
	private String alipay; //支付宝帐号
	
	private String qqpay; //财付通帐号
	
	private String recommended; //推荐人
	
	private String identification_card; //身份证
	
	private String isauthentication; //是否已实名认证，1代表已经实名认证
	
	private String objid; //对象id，以后可能会绑定某张表的某个字段
	
	private Integer seecount; //浏览次数
	
	private Double sortcode; //排序码
	
	private String createdate; //创建日期
	
	private String createuser; //创建人
	
	private String updatedate; //修改时间

	private String updateuser; //修改人
	
	private String deletemark="0"; //删除标记
	
	private String permitids;
	
	private List roles; //对应角色
	
	private List  permits; //用户对应的权限
	
	private String ismodule="0"; //是否调用动态模块 0为不调用，1为调用
	
	private Long bbs_charm=Long.parseLong("0"); //论坛魅力值
	
	private Long bbs_gold=Long.parseLong("0"); //论坛金币
	
	private String bbs_rank="新注册用户"; //论坛等级
	
	private String bbs_ranknum="0";
	
	private Integer bbs_postnum=0; //发帖数
	
	private String bbs_admin; //论坛管理员
	
	private String isadmin="0"; //系统管理员  1 为系统管理员 
	
	private String loginIp;
	
	private String unreadsize="0"; //未读信息，不记录数据库
	
    private String pstr=",";  //用户功能权限字符串组合，不记录数据库
	
	private String mstr=",";  //用户模块查看权限字符串组合，不记录数据库
	
	private String systemmodule=","; //系统拥有需权限配置的模块字符串组合，不记录数据库
	
	private String systempermit; //系统拥有需权限配置的模块字符串组合，不记录数据库
	
	private String allpermits; //用户拥有的所有权限，在查看用户所有权限中使用
	
	private String systemstopmodules; //系统禁用模块
	
	private List modulelist; //后台模块列表，不记录数据库,主要是用来把系统展示的后台模块记录session
	
	private String usertype; //用户类型 ，例如后台用户为：backstage_user
	
	private String usertypevalue; //指定用户类型下 ，对应的一些标识值，用于一些权限复杂的系统
	
	private String provinceid;
	
	private String cityid;
	
	private String districtid;
	
	private String streetid;
	
	private String communityid;
	
	private Long optime;//操作时间
	
	private String audit_pic1; //认证照片1
	
	private String audit_pic2;//认证照片2
	
	private String audit_pic3; //认证照片3
	
	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getDistrictid() {
		return districtid;
	}

	public void setDistrictid(String districtid) {
		this.districtid = districtid;
	}

	public String getStreetid() {
		return streetid;
	}

	public void setStreetid(String streetid) {
		this.streetid = streetid;
	}

	public String getCommunityid() {
		return communityid;
	}

	public void setCommunityid(String communityid) {
		this.communityid = communityid;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getBbs_postnum() {
		return bbs_postnum;
	}

	public void setBbs_postnum(Integer bbs_postnum) {
		this.bbs_postnum = bbs_postnum;
	}

	public String getBbs_ranknum() {
		return bbs_ranknum;
	}

	public void setBbs_ranknum(String bbs_ranknum) {
		this.bbs_ranknum = bbs_ranknum;
	}

	public List getModulelist() {
		return modulelist;
	}

	public void setModulelist(List modulelist) {
		this.modulelist = modulelist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstvisit() {
		return firstvisit;
	}

	public void setFirstvisit(String firstvisit) {
		this.firstvisit = firstvisit;
	}

	public String getPreviousvisit() {
		return previousvisit;
	}

	public void setPreviousvisit(String previousvisit) {
		this.previousvisit = previousvisit;
	}

	public String getLastvisit() {
		return lastvisit;
	}

	public void setLastvisit(String lastvisit) {
		this.lastvisit = lastvisit;
	}

	public Integer getLogincount() {
		return logincount;
	}

	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}

	public String getIsstaff() {
		return isstaff;
	}

	public void setIsstaff(String isstaff) {
		this.isstaff = isstaff;
	}

	public String getAuditstatus() {
		return auditstatus;
	}

	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}

	public String getOwnsigned() {
		return ownsigned;
	}

	public void setOwnsigned(String ownsigned) {
		this.ownsigned = ownsigned;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getUseronline() {
		return useronline;
	}

	public void setUseronline(String useronline) {
		this.useronline = useronline;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public String getHomeaddress() {
		return homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}

	public String getQusetion() {
		return qusetion;
	}

	public void setQusetion(String qusetion) {
		this.qusetion = qusetion;
	}

	public String getAnswerquesetion() {
		return answerquesetion;
	}

	public void setAnswerquesetion(String answerquesetion) {
		this.answerquesetion = answerquesetion;
	}

	public String getUserfrom() {
		return userfrom;
	}

	public void setUserfrom(String userfrom) {
		this.userfrom = userfrom;
	}

	public String getDeletemark() {
		return deletemark;
	}

	public void setDeletemark(String deletemark) {
		this.deletemark = deletemark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Double getSortcode() {
		return sortcode;
	}

	public void setSortcode(Double sortcode) {
		this.sortcode = sortcode;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getUnreadsize() {
		return unreadsize;
	}

	public void setUnreadsize(String unreadsize) {
		this.unreadsize = unreadsize;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List getRoles() {
		return roles;
	}

	public void setRoles(List roles) {
		this.roles = roles;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getIsmodule() {
		return ismodule;
	}

	public void setIsmodule(String ismodule) {
		this.ismodule = ismodule;
	}

	public String getPositionid() {
		return positionid;
	}

	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}


	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getAllpermits() {
		return allpermits;
	}

	public void setAllpermits(String allpermits) {
		this.allpermits = allpermits;
	}

	public Long getBbs_charm() {
		return bbs_charm;
	}

	public void setBbs_charm(Long bbs_charm) {
		this.bbs_charm = bbs_charm;
	}

	public Long getBbs_gold() {
		return bbs_gold;
	}

	public void setBbs_gold(Long bbs_gold) {
		this.bbs_gold = bbs_gold;
	}

	public String getBbs_rank() {
		return bbs_rank;
	}

	public void setBbs_rank(String bbs_rank) {
		this.bbs_rank = bbs_rank;
	}

	public String getBbs_admin() {
		return bbs_admin;
	}

	public void setBbs_admin(String bbs_admin) {
		this.bbs_admin = bbs_admin;
	}

	public String getPstr() {
		return pstr;
	}

	public void setPstr(String pstr) {
		this.pstr = pstr;
	}

	public String getMstr() {
		return mstr;
	}

	public void setMstr(String mstr) {
		this.mstr = mstr;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public String getSystempermit() {
		return systempermit;
	}

	public void setSystempermit(String systempermit) {
		this.systempermit = systempermit;
	}

	public String getSystemmodule() {
		return systemmodule;
	}

	public void setSystemmodule(String systemmodule) {
		this.systemmodule = systemmodule;
	}

	public String getPermitids() {
		return permitids;
	}

	public void setPermitids(String permitids) {
		this.permitids = permitids;
	}

	public String getSystemstopmodules() {
		return systemstopmodules;
	}

	public void setSystemstopmodules(String systemstopmodules) {
		this.systemstopmodules = systemstopmodules;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String udateuser) {
		this.updateuser = udateuser;
	}

	public String getIswanshan() {
		return iswanshan;
	}

	public void setIswanshan(String iswanshan) {
		this.iswanshan = iswanshan;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getBusiness_license() {
		return business_license;
	}

	public void setBusiness_license(String businessLicense) {
		business_license = businessLicense;
	}

	public String getOrganization_code() {
		return organization_code;
	}

	public void setOrganization_code(String organizationCode) {
		organization_code = organizationCode;
	}

	public String getTax_registration_certificate() {
		return tax_registration_certificate;
	}

	public void setTax_registration_certificate(String taxRegistrationCertificate) {
		tax_registration_certificate = taxRegistrationCertificate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bankAccount) {
		bank_account = bankAccount;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getQqpay() {
		return qqpay;
	}

	public void setQqpay(String qqpay) {
		this.qqpay = qqpay;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}

	public String getIdentification_card() {
		return identification_card;
	}

	public void setIdentification_card(String identificationCard) {
		identification_card = identificationCard;
	}
	
	public Integer getSeecount() {
		return seecount;
	}

	public void setSeecount(Integer seecount) {
		this.seecount = seecount;
	}
	
	public List getPermits() {
		return permits;
	}

	public void setPermits(List permits) {
		this.permits = permits;
	}

	public String getUsertypevalue() {
		return usertypevalue;
	}

	public void setUsertypevalue(String usertypevalue) {
		this.usertypevalue = usertypevalue;
	}

	public Long getOptime() {
		return optime;
	}

	public void setOptime(Long optime) {
		this.optime = optime;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPositionname() {
		return positionname;
	}

	public void setPositionname(String positionname) {
		this.positionname = positionname;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getPolitical_status() {
		return political_status;
	}

	public void setPolitical_status(String politicalStatus) {
		political_status = politicalStatus;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getIsauthentication() {
		return isauthentication;
	}

	public void setIsauthentication(String isauthentication) {
		this.isauthentication = isauthentication;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public Double getIntegral() {
		return integral;
	}

	public void setIntegral(Double integral) {
		this.integral = integral;
	}

	public String getAudit_pic1() {
		return audit_pic1;
	}

	public void setAudit_pic1(String auditPic1) {
		audit_pic1 = auditPic1;
	}

	public String getAudit_pic2() {
		return audit_pic2;
	}

	public void setAudit_pic2(String auditPic2) {
		audit_pic2 = auditPic2;
	}

	public String getAudit_pic3() {
		return audit_pic3;
	}

	public void setAudit_pic3(String auditPic3) {
		audit_pic3 = auditPic3;
	}
	
	
}