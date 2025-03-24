package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.XuexizujiEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 学习足迹
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("xuexizuji")
public class XuexizujiView extends XuexizujiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	* 学习状态的值
	*/
	@ColumnInfo(comment="学习状态的字典表值",type="varchar(200)")
	private String xuexizujiValue;

	//级联表 视频信息
		/**
		* 视频标题
		*/

		@ColumnInfo(comment="视频标题",type="varchar(200)")
		private String shipinName;
		/**
		* 视频照片
		*/

		@ColumnInfo(comment="视频照片",type="varchar(200)")
		private String shipinPhoto;
		/**
		* 视频
		*/

		@ColumnInfo(comment="视频",type="varchar(200)")
		private String shipinVideo;
		/**
		* 视频类型
		*/
		@ColumnInfo(comment="视频类型",type="int(11)")
		private Integer shipinTypes;
			/**
			* 视频类型的值
			*/
			@ColumnInfo(comment="视频类型的字典表值",type="varchar(200)")
			private String shipinValue;
		/**
		* 备课详情
		*/

		@ColumnInfo(comment="备课详情",type="text")
		private String shipinContent;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer shipinDelete;
	//级联表 用户
		/**
		* 用户姓名
		*/

		@ColumnInfo(comment="用户姓名",type="varchar(200)")
		private String yonghuName;
		/**
		* 用户手机号
		*/

		@ColumnInfo(comment="用户手机号",type="varchar(200)")
		private String yonghuPhone;
		/**
		* 用户身份证号
		*/

		@ColumnInfo(comment="用户身份证号",type="varchar(200)")
		private String yonghuIdNumber;
		/**
		* 用户头像
		*/

		@ColumnInfo(comment="用户头像",type="varchar(200)")
		private String yonghuPhoto;
		/**
		* 学习时长
		*/

		@ColumnInfo(comment="学习时长",type="int(11)")
		private Integer xuexiNumber;
		/**
		* 电子邮箱
		*/

		@ColumnInfo(comment="电子邮箱",type="varchar(200)")
		private String yonghuEmail;

	//重复字段
			/**
			* 重复字段 的types
			*/
			@ColumnInfo(comment="重复字段 的types",type="Integer")
			private Integer kemuTypes;
				@ColumnInfo(comment="重复字段 的值",type="varchar(200)")
				private String kemuValue;


	public XuexizujiView() {

	}

	public XuexizujiView(XuexizujiEntity xuexizujiEntity) {
		try {
			BeanUtils.copyProperties(this, xuexizujiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//当前表的
	/**
	* 获取： 学习状态的值
	*/
	public String getXuexizujiValue() {
		return xuexizujiValue;
	}
	/**
	* 设置： 学习状态的值
	*/
	public void setXuexizujiValue(String xuexizujiValue) {
		this.xuexizujiValue = xuexizujiValue;
	}


	//级联表的get和set 视频信息

		/**
		* 获取： 视频标题
		*/
		public String getShipinName() {
			return shipinName;
		}
		/**
		* 设置： 视频标题
		*/
		public void setShipinName(String shipinName) {
			this.shipinName = shipinName;
		}

		/**
		* 获取： 视频照片
		*/
		public String getShipinPhoto() {
			return shipinPhoto;
		}
		/**
		* 设置： 视频照片
		*/
		public void setShipinPhoto(String shipinPhoto) {
			this.shipinPhoto = shipinPhoto;
		}

		/**
		* 获取： 视频
		*/
		public String getShipinVideo() {
			return shipinVideo;
		}
		/**
		* 设置： 视频
		*/
		public void setShipinVideo(String shipinVideo) {
			this.shipinVideo = shipinVideo;
		}
		/**
		* 获取： 视频类型
		*/
		public Integer getShipinTypes() {
			return shipinTypes;
		}
		/**
		* 设置： 视频类型
		*/
		public void setShipinTypes(Integer shipinTypes) {
			this.shipinTypes = shipinTypes;
		}


			/**
			* 获取： 视频类型的值
			*/
			public String getShipinValue() {
				return shipinValue;
			}
			/**
			* 设置： 视频类型的值
			*/
			public void setShipinValue(String shipinValue) {
				this.shipinValue = shipinValue;
			}

		/**
		* 获取： 备课详情
		*/
		public String getShipinContent() {
			return shipinContent;
		}
		/**
		* 设置： 备课详情
		*/
		public void setShipinContent(String shipinContent) {
			this.shipinContent = shipinContent;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getShipinDelete() {
			return shipinDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setShipinDelete(Integer shipinDelete) {
			this.shipinDelete = shipinDelete;
		}
	//级联表的get和set 用户

		/**
		* 获取： 用户姓名
		*/
		public String getYonghuName() {
			return yonghuName;
		}
		/**
		* 设置： 用户姓名
		*/
		public void setYonghuName(String yonghuName) {
			this.yonghuName = yonghuName;
		}

		/**
		* 获取： 用户手机号
		*/
		public String getYonghuPhone() {
			return yonghuPhone;
		}
		/**
		* 设置： 用户手机号
		*/
		public void setYonghuPhone(String yonghuPhone) {
			this.yonghuPhone = yonghuPhone;
		}

		/**
		* 获取： 用户身份证号
		*/
		public String getYonghuIdNumber() {
			return yonghuIdNumber;
		}
		/**
		* 设置： 用户身份证号
		*/
		public void setYonghuIdNumber(String yonghuIdNumber) {
			this.yonghuIdNumber = yonghuIdNumber;
		}

		/**
		* 获取： 用户头像
		*/
		public String getYonghuPhoto() {
			return yonghuPhoto;
		}
		/**
		* 设置： 用户头像
		*/
		public void setYonghuPhoto(String yonghuPhoto) {
			this.yonghuPhoto = yonghuPhoto;
		}

		/**
		* 获取： 学习时长
		*/
		public Integer getXuexiNumber() {
			return xuexiNumber;
		}
		/**
		* 设置： 学习时长
		*/
		public void setXuexiNumber(Integer xuexiNumber) {
			this.xuexiNumber = xuexiNumber;
		}

		/**
		* 获取： 电子邮箱
		*/
		public String getYonghuEmail() {
			return yonghuEmail;
		}
		/**
		* 设置： 电子邮箱
		*/
		public void setYonghuEmail(String yonghuEmail) {
			this.yonghuEmail = yonghuEmail;
		}

	//重复字段
			/**
			* 获取： 重复字段 的types
			*/
			public Integer getKemuTypes() {
			return kemuTypes;
			}
			/**
			* 设置： 重复字段 的types
			*/
			public void setKemuTypes(Integer kemuTypes) {
			this.kemuTypes = kemuTypes;
			}
				public String getKemuValue() {
				return kemuValue;
				}
				public void setKemuValue(String kemuValue) {
				this.kemuValue = kemuValue;
				}

	@Override
	public String toString() {
		return "XuexizujiView{" +
			", xuexizujiValue=" + xuexizujiValue +
			", yonghuName=" + yonghuName +
			", yonghuPhone=" + yonghuPhone +
			", yonghuIdNumber=" + yonghuIdNumber +
			", yonghuPhoto=" + yonghuPhoto +
			", xuexiNumber=" + xuexiNumber +
			", yonghuEmail=" + yonghuEmail +
			", shipinName=" + shipinName +
			", shipinPhoto=" + shipinPhoto +
			", shipinVideo=" + shipinVideo +
			", shipinContent=" + shipinContent +
			", shipinDelete=" + shipinDelete +
			"} " + super.toString();
	}
}
