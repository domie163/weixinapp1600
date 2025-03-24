<template>
    <view class="content">
        <form class="app-update-pv">
            <view
                    :style='{"boxShadow":"0","backgroundColor":"rgba(255, 255, 255, 0.25)","borderColor":"rgba(228, 232, 230, 1)","margin":"0 0 0px 0","borderWidth":"2rpx","borderStyle":"none none solid none ","height":"100rpx"}'
                    class="cu-form-group">
                <view :style='{"width":"160rpx","fontSize":"28rpx","color":"var(--publicMainColor)","textAlign":"left"}'
                      class="title">试卷名称</view>
                <input   disabled
                         :style='{"padding":"0 24rpx","boxShadow":"0 10rpx 0px rgba(0,0,0,0)","margin":"0","borderColor":"var(--publicMainColor)","backgroundColor":"#fff","color":"rgba(0, 0, 0, 1)","textAlign":"left","borderRadius":"40rpx","borderWidth":"2rpx 2rpx 10rpx 2rpx","fontSize":"26rpx","borderStyle":"solid","height":"88rpx","marginTop":"7rpx"}'
                         :disabled="ro.exampaperName" type="text" v-model="ruleForm.exampaperName" placeholder="试卷名称"></input>
            </view>
                <view
                        :style='{"boxShadow":"0","backgroundColor":"rgba(255, 255, 255, 0.25)","borderColor":"rgba(228, 232, 230, 1)","margin":"0 0 0px 0","borderWidth":"2rpx","borderStyle":"none none solid none ","height":"100rpx"}'
                        class="cu-form-group">
                    <view :style='{"width":"160rpx","fontSize":"28rpx","color":"var(--publicMainColor)","textAlign":"left"}'
                          class="title">考试时长(分钟)</view>
                    <input   disabled
                             :style='{"padding":"0 24rpx","boxShadow":"0 10rpx 0px rgba(0,0,0,0)","margin":"0","borderColor":"var(--publicMainColor)","backgroundColor":"#fff","color":"rgba(0, 0, 0, 1)","textAlign":"left","borderRadius":"40rpx","borderWidth":"2rpx 2rpx 10rpx 2rpx","fontSize":"26rpx","borderStyle":"solid","height":"88rpx","marginTop":"7rpx"}'
                             :disabled="ro.exampaperDate" type="number" v-model="ruleForm.exampaperDate" placeholder="考试时长(分钟)"></input>
                </view>
                <view
                        :style='{"boxShadow":"0","backgroundColor":"rgba(255, 255, 255, 0.25)","borderColor":"rgba(228, 232, 230, 1)","margin":"0 0 0px 0","borderWidth":"2rpx","borderStyle":"none none solid none ","height":"100rpx"}'
                        class="cu-form-group">
                    <view :style='{"width":"160rpx","fontSize":"28rpx","color":"var(--publicMainColor)","textAlign":"left"}'
                          class="title">试卷总分数</view>
                    <input   disabled
                             :style='{"padding":"0 24rpx","boxShadow":"0 10rpx 0px rgba(0,0,0,0)","margin":"0","borderColor":"var(--publicMainColor)","backgroundColor":"#fff","color":"rgba(0, 0, 0, 1)","textAlign":"left","borderRadius":"40rpx","borderWidth":"2rpx 2rpx 10rpx 2rpx","fontSize":"26rpx","borderStyle":"solid","height":"88rpx","marginTop":"7rpx"}'
                             :disabled="ro.exampaperMyscore" type="number" v-model="ruleForm.exampaperMyscore" placeholder="试卷总分数"></input>
                </view>
                <view :style='{"boxShadow":"0","backgroundColor":"rgba(255, 255, 255, 0.25)","borderColor":"rgba(228, 232, 230, 1)","margin":"0 0 0px 0","borderWidth":"2rpx","borderStyle":"none none solid none ","height":"100rpx"}'
                      class="cu-form-group">
                    <view :style='{"width":"160rpx","fontSize":"28rpx","color":"var(--publicMainColor)","textAlign":"left"}'
                          class="title">科目</view>
                    <picker @change="kemuTypesChange" :value="kemuTypesIndex" :range="kemuTypesOptions" range-key="indexName">
                        <view
                                :style='{"padding":"0 24rpx","boxShadow":"0 10rpx 0px rgba(0,0,0,0)","margin":"0","borderColor":"var(--publicMainColor)","backgroundColor":"#fff","color":"rgba(0, 0, 0, 1)","textAlign":"left","borderRadius":"40rpx","borderWidth":"2rpx 2rpx 10rpx 2rpx","fontSize":"26rpx","borderStyle":"solid","height":"88rpx","marginTop":"7rpx"}'
                                class="uni-input">{{ruleForm.kemuTypes?ruleForm.kemuValue:"请选择科目"}}</view>
                    </picker>
                </view>
                <view :style='{"boxShadow":"0","backgroundColor":"rgba(255, 255, 255, 0.25)","borderColor":"rgba(228, 232, 230, 1)","margin":"0 0 0px 0","borderWidth":"2rpx","borderStyle":"none none solid none ","height":"100rpx"}'
                      class="cu-form-group">
                    <view :style='{"width":"160rpx","fontSize":"28rpx","color":"var(--publicMainColor)","textAlign":"left"}'
                          class="title">试卷状态</view>
                    <picker @change="exampaperTypesChange" :value="exampaperTypesIndex" :range="exampaperTypesOptions" range-key="indexName">
                        <view
                                :style='{"padding":"0 24rpx","boxShadow":"0 10rpx 0px rgba(0,0,0,0)","margin":"0","borderColor":"var(--publicMainColor)","backgroundColor":"#fff","color":"rgba(0, 0, 0, 1)","textAlign":"left","borderRadius":"40rpx","borderWidth":"2rpx 2rpx 10rpx 2rpx","fontSize":"26rpx","borderStyle":"solid","height":"88rpx","marginTop":"7rpx"}'
                                class="uni-input">{{ruleForm.exampaperTypes?ruleForm.exampaperValue:"请选择试卷状态"}}</view>
                    </picker>
                </view>
                <view :style='{"boxShadow":"0","backgroundColor":"rgba(255, 255, 255, 0.25)","borderColor":"rgba(228, 232, 230, 1)","margin":"0 0 0px 0","borderWidth":"2rpx","borderStyle":"none none solid none ","height":"100rpx"}'
                      class="cu-form-group">
                    <view :style='{"width":"160rpx","fontSize":"28rpx","color":"var(--publicMainColor)","textAlign":"left"}'
                          class="title">组卷方式</view>
                    <picker @change="zujuanTypesChange" :value="zujuanTypesIndex" :range="zujuanTypesOptions" range-key="indexName">
                        <view
                                :style='{"padding":"0 24rpx","boxShadow":"0 10rpx 0px rgba(0,0,0,0)","margin":"0","borderColor":"var(--publicMainColor)","backgroundColor":"#fff","color":"rgba(0, 0, 0, 1)","textAlign":"left","borderRadius":"40rpx","borderWidth":"2rpx 2rpx 10rpx 2rpx","fontSize":"26rpx","borderStyle":"solid","height":"88rpx","marginTop":"7rpx"}'
                                class="uni-input">{{ruleForm.zujuanTypes?ruleForm.zujuanValue:"请选择组卷方式"}}</view>
                    </picker>
                </view>

            <view class="btn">
                <button
                        :style='{"boxShadow":"0 0 16rpx rgba(0,0,0,0) inset","backgroundColor":"var(--publicMainColor)","borderColor":"#409EFF","borderRadius":"8rpx","color":"rgba(255, 255, 255, 1)","borderWidth":"0","width":"70%","fontSize":"32rpx","borderStyle":"solid","height":"80rpx"}'
                        @tap="onSubmitTap" class="bg-red">提交</button>
            </view>
        </form>

					<w-picker mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="createTimeConfirm"
                              ref="createTime" themeColor="#333333"></w-picker>


    </view>
</template>

<script>
    import wPicker from "@/components/w-picker/w-picker.vue";

    export default {
        data() {
            return {
                cross: '',
                ro:{
					exampaperName: false,
					exampaperDate: false,
					exampaperMyscore: false,
					kemuTypes: false,
					exampaperTypes: false,
					zujuanTypes: false,
					exampaperDelete: false,
					createTime: false,
        },
            ruleForm: {
					exampaperName: '',
					exampaperDate: '',
					exampaperMyscore: '',
					kemuTypes: '',//数字
					kemuValue: '',//数字对应的值
					exampaperTypes: '',//数字
					exampaperValue: '',//数字对应的值
					zujuanTypes: '',//数字
					zujuanValue: '',//数字对应的值
					exampaperDelete: '',
					createTime: '',
            },
            // 登陆用户信息
            user: {},
				kemuTypesOptions: [],
							kemuTypesIndex : 0,
				exampaperTypesOptions: [],
							exampaperTypesIndex : 0,
				zujuanTypesOptions: [],
							zujuanTypesIndex : 0,

        }
        },
        components: {
            wPicker
        },
        computed: {
            baseUrl() {
                return this.$base.url;
            },
        },
        async onLoad(options) {

		/*下拉框*/
			let kemuTypesParams = {
                page: 1,
                limit: 100,
                dicCode: 'kemu_types',
            }
			let kemuTypes = await this.$api.page(`dictionary`, kemuTypesParams);
			this.kemuTypesOptions = kemuTypes.data.list
		/*下拉框*/
			let exampaperTypesParams = {
                page: 1,
                limit: 100,
                dicCode: 'exampaper_types',
            }
			let exampaperTypes = await this.$api.page(`dictionary`, exampaperTypesParams);
			this.exampaperTypesOptions = exampaperTypes.data.list
		/*下拉框*/
			let zujuanTypesParams = {
                page: 1,
                limit: 100,
                dicCode: 'zujuan_types',
            }
			let zujuanTypes = await this.$api.page(`dictionary`, zujuanTypesParams);
			this.zujuanTypesOptions = zujuanTypes.data.list


            // 如果是更新操作
            if (options.id) {
                this.ruleForm.id = options.id;
                // 获取信息
                let res = await this.$api.info(`exampaper`, this.ruleForm.id);
                this.ruleForm = res.data;
            }
            if(options.exampaperId){
                this.ruleForm.exampaperId = options.exampaperId;
            }
            // 跨表
            // this.styleChange()
        },
        methods: {
            // 下拉变化
            kemuTypesChange(e) {
                this.kemuTypesIndex = e.target.value
                this.ruleForm.kemuValue = this.kemuTypesOptions[this.kemuTypesIndex].indexName
                this.ruleForm.kemuTypes = this.kemuTypesOptions[this.kemuTypesIndex].codeIndex
            },
            exampaperTypesChange(e) {
                this.exampaperTypesIndex = e.target.value
                this.ruleForm.exampaperValue = this.exampaperTypesOptions[this.exampaperTypesIndex].indexName
                this.ruleForm.exampaperTypes = this.exampaperTypesOptions[this.exampaperTypesIndex].codeIndex
            },
            zujuanTypesChange(e) {
                this.zujuanTypesIndex = e.target.value
                this.ruleForm.zujuanValue = this.zujuanTypesOptions[this.zujuanTypesIndex].indexName
                this.ruleForm.zujuanTypes = this.zujuanTypesOptions[this.zujuanTypesIndex].codeIndex
            },


            // styleChange() {
            // 	this.$nextTick(() => {
            // 		// document.querySelectorAll('.app-update-pv .cu-form-group .uni-yaoxianStyle-yaoxianStyle').forEach(el=>{
            // 		//   el.style.backgroundColor = this.addUpdateForm.yaoxianStyle.content.backgroundColor
            // 		// })
            // 	})
            // },


			// 日期控件
			createTimeConfirm(val) {
                this.ruleForm.createTime = val.result;
                this.$forceUpdate();
            },




            getUUID() {
                return new Date().getTime();
            },
            async onSubmitTap() {
				if ((!this.ruleForm.exampaperName)) {
                    this.$utils.msg(`试卷名称不能为空`);
                    return
                }
				if ((!this.ruleForm.exampaperDate) && (!this.$validate.isIntNumer(this.ruleForm.exampaperDate)) && this.ruleForm.exampaperDate >0) {
                    this.$utils.msg(`考试时长(分钟)不能为空，不能小于0 格式为数字`);
                    return
                }
				if ((!this.ruleForm.exampaperMyscore) && (!this.$validate.isIntNumer(this.ruleForm.exampaperMyscore)) && this.ruleForm.exampaperMyscore >0) {
                    this.$utils.msg(`试卷总分数不能为空，不能小于0 格式为数字`);
                    return
                }
				if ((!this.ruleForm.kemuTypes)) {
                    this.$utils.msg(`科目不能为空`);
                    return
                }
				if ((!this.ruleForm.zujuanTypes)) {
                    this.$utils.msg(`组卷方式不能为空`);
                    return
                }
                if (this.ruleForm.id) {
                    await this.$api.update(`exampaper`, this.ruleForm);
                } else {
                    await this.$api.save(`exampaper`, this.ruleForm);
                }
                uni.setStorageSync('pingluenStateState', true);
                this.$utils.msgBack('提交成功');
            },
            getDate(type) {
                const date = new Date();
                let year = date.getFullYear();
                let month = date.getMonth() + 1;
                let day = date.getDate();
                if (type === 'start') {
                    year = year - 60;
                } else if (type === 'end') {
                    year = year + 2;
                }
                month = month > 9 ? month : '0' + month;;
                day = day > 9 ? day : '0' + day;
                return `${year}-${month}-${day}`;
            },
            toggleTab(str) {
                this.$refs[str].show();
            }
        }
    }
</script>
<style lang="scss" scoped>
    .container {
        padding: 20upx;
    }

    .content:after {
        position: fixed;
        top: 0;
        right: 0;
        left: 0;
        bottom: 0;
        content: '';
        background-attachment: fixed;
        background-size: cover;
        background-position: center;
    }

    textarea {
        border: 1upx solid #EEEEEE;
        border-radius: 20upx;
        padding: 20upx;
    }

    .title {
        width: 180upx;
    }

    .avator {
        width: 150upx;
        height: 60upx;
    }

    .right-input {
        flex: 1;
        text-align: left;
        padding: 0 24upx;
    }

    .cu-form-group.active {
        justify-content: space-between;
    }

    .cu-form-group .title {
        height: auto;
        line-height:30rpx
    }

    .btn {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-wrap: wrap;
        padding: 20upx 0;
    }

    .cu-form-group {
        padding: 0 24upx;
        background-color: transparent;
        min-height: inherit;
    }

    .cu-form-group+.cu-form-group {
        border: 0;
    }

    .cu-form-group uni-input {
        padding: 0 30upx;
    }

    .uni-input {
        padding: 0 30upx;
    }

    .cu-form-group uni-textarea {
        padding: 30upx;
        margin: 0;
    }

    .cu-form-group uni-picker::after {
        line-height: 80rpx;
    }

    .select .uni-input {
        line-height: 80rpx;
    }

    .input .right-input {
        line-height: 88rpx;
    }
</style>