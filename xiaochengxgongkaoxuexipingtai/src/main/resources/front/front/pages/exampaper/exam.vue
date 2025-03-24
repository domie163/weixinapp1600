<template>
    <view class="box">
        <!-- 答题试卷区域 -->
        <view class="datilist">
            <view class="" style="height: 100rpx;text-align: center;line-height: 90rpx;font-size: 40rpx;">
                {{exampaperName}}
            </view>
            <view class="exam">
                <view class="datilist-List">
                    <uni-icons type="notification" size='30'></uni-icons>
                    <text>{{SecondToDate}}</text>
                </view>
                <view class="datilist-List">
                    <text v-if="count>0">考试中</text>
                    <text v-else>考试结束</text>
                </view>
            </view>
        </view>
        <!-- 答题区域 -->
        <view class="datidata">
            <swiper class="swiper" :interval="3000" :duration="200">
                <swiper-item v-for="(item,index) in dataList">
                    <view class="swiper-item">
                        <view class="swiper-text">
                            <view class="" style="display: flex;justify-content: space-between;">
                                <text>{{item.examquestionValue}}/{{item.exampapertopicNumber}}分</text>
                                <view class="datilist-List">
                                    <text style="font-size: 40rpx;"><text style="color: green">{{index+1}}</text>/</text>{{dataList.length}}
                                </view>

                            </view>
                            <view class="topic">
                                <text>{{index+1}}、</text>
                                <text>{{item.examquestionName}}</text>
                            </view>

                        </view>
                        <view class="swiper-list ">
                            <checkbox-group class="swiper-list" v-if="item.examquestionTypes == 2">
                                <label @tap="selectQuestions(item.examquestionId,io.code)"
                                       v-for="(io) in item.examquestionOptions" v-bind:key="io.code"
                                       style="display: flex;padding-top: 50rpx;">
                                    <checkbox :value="io.code" />{{io.code}}、
                                    <view>{{io.text}}</view>
                                </label>
                            </checkbox-group>
                            <!-- 、单选框	 -->
                            <radio-group class="swiper-list"
                                         v-else-if="item.examquestionTypes == 1 || item.examquestionTypes == 3">
                                <label @tap="selectQuestions(item.examquestionId,io.code)"
                                       v-for="(io) in item.examquestionOptions" v-bind:key="io.code"
                                       style="display: flex;padding-top: 50rpx;">
                                    <radio :value="io.code" />{{io.code}}、
                                    <view>{{io.text}}</view>
                                </label>
                            </radio-group>
                            <!-- 填空题 -->
                            <textarea v-else maxlength="-1" v-model="answerList[index].answer"
                                      style="width: 650rpx;height: 300rpx;border: 1rpx solid #d0cbcb;border-radius: 15rpx;padding: 10rpx;" />
                        </view>
                        <view class="" v-show="index === 0" style="width: 489rpx;
						 height: 286rpx;position: relative;
						 left: 170rpx;
						 top: 310rpx;">
                            <image src="../../static/kaosi/tips.jpg" mode=""></image>
                        </view>
                    </view>
                </swiper-item>
            </swiper>
        </view>
        <view class="submit">
            <view class="submit-item" @click="submitQuestions">
                提交
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                show: true,
                //考试题目
                dataList: [],
                // 倒计时定时器
                inter: null,
                // 倒计时时间
                count: 0,
                //用户选择答案
                answerList: [],
                //试卷名称
                exampaperName: null,
                //试卷总分数
                exampaperMyscore: null,
                //用户信息
                user: {},
                table: null,
                //试卷表Id
                exampaperId: null,
            };
        },
        computed: {
            SecondToDate: function() {
                var time = this.count;
                if (null != time && "" != time) {
                    if (time > 60 && time < 60 * 60) {
                        time =
                                parseInt(time / 60.0) +
                                "分钟" +
                                parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60) +
                                "秒";
                    } else if (time >= 60 * 60 && time < 60 * 60 * 24) {
                        time =
                                parseInt(time / 3600.0) +
                                "小时" +
                                parseInt(
                                        (parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
                                ) +
                                "分钟" +
                                parseInt(
                                        (parseFloat(
                                                (parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
                                                ) -
                                                parseInt(
                                                        (parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
                                                )) *
                                        60
                                ) +
                                "秒";
                    } else if (time >= 60 * 60 * 24) {
                        time =
                                parseInt(time / 3600.0 / 24) +
                                "天" +
                                parseInt(
                                        (parseFloat(time / 3600.0 / 24) - parseInt(time / 3600.0 / 24)) *
                                        24
                                ) +
                                "小时" +
                                parseInt(
                                        (parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
                                ) +
                                "分钟" +
                                parseInt(
                                        (parseFloat(
                                                (parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
                                                ) -
                                                parseInt(
                                                        (parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
                                                )) *
                                        60
                                ) +
                                "秒";
                    } else {
                        time = parseInt(time) + "秒";
                    }
                }
                return time;
            }
        },
        async onLoad(data) {
            let _this = this
            _this.exampaperId = data.exampaperId
            _this.table = uni.getStorageSync("nowTable");
            //查询登录用户信息
            let userData = await _this.$api.session(_this.table);
            _this.user = userData.data;
            //获取选题表试题信息
            let record = [{
                key: "exampaperId",
                val: data.exampaperId,
            }];
            let dataA = await _this.$api.requestCondition(`exampapertopic`, 'questionAcquisition', record);
            _this.dataList = dataA.data;
            _this.exampaperName = _this.dataList[0].exampaperName
            _this.exampaperMyscore = _this.dataList[0].exampaperMyscore
            //定时
            _this.count = _this.dataList[0].exampaperDate * 60;
            if (_this.count > 0) {
                _this.inter = setInterval(function() {
                    _this.count = _this.count - 1;
                    if (_this.count < 0) {
                        //时间结束结束考试
                        _this.submitQuestions()
                    }
                }, 1000);
            }

            //转换 单多选 判断题的选项
            _this.dataList.forEach(function(item, index) {
                if (item.examquestionTypes != 4) {
                    item.examquestionOptions = JSON.parse(item.examquestionOptions);
                }
                let examquestionId = item.examquestionId
                let examquestionTypes = item.examquestionTypes
                _this.answerList.push({
                    examquestionId: examquestionId,
                    examquestionTypes: examquestionTypes,
                    answer: ""
                })
            })
        },
        destroyed: function() {
            clearInterval(this.inter);
        },
        methods: {
            //跳转
            async goTo(id) {
                document.querySelector('#id' + id).scrollIntoView()
            },
            //用户选择题答案
            selectQuestions(examquestionId, code) {
                this.answerList.forEach(function(item, index) {
                    if (item.examquestionId === examquestionId) {
                        if (item.examquestionTypes == 2) {
                            if (item.answer == "") { //空的
                                item.answer = code;
                            } else if (item.answer == code) { //删除最后一个
                                item.answer = "";
                            } else {
                                let answerList = item.answer.split(","); //切割数组
                                if (answerList.indexOf(code) != -1) { //存在
                                    answerList.splice(answerList.indexOf(code), 1)
                                    item.answer = answerList.join(",")
                                } else { //不存在
                                    answerList.push(code)
                                    item.answer = answerList.join(",")
                                }
                            }
                        } else {
                            item.answer = code
                        }
                    }
                })
            },
            //提交试卷
            async submitQuestions() {
                let _this = this
                let notDone = []
                let params = {
                    yonghuId: _this.user.id,
                    answerList: JSON.stringify(_this.answerList),
                    exampaperId: _this.exampaperId
                }
                _this.dataList.forEach(function(item, index) {
                    if (item.examquestionId == _this.answerList[index].examquestionId && (_this.answerList[
                                    index]
                                    .answer == null || _this.answerList[index].answer == '')) {
                        notDone.push(index + 1)
                    }
                })
                if (_this.count > 0) {
                    if (notDone.length > 0) {
                        _this.$utils.msg("您有’" + notDone.length + "‘道题未作，分别为：" + notDone + ";请继续答题！");
                        return false
                    }
                } else {
                    if (notDone.length > 0) {
                        _this.$utils.msg("您有’" + notDone.length + "‘道题未作，分别为：" + notDone + ";未做题目为0分！");
                    } else {
                        _this.$utils.msg("答题时间已到即将提交试卷！！！");
                    }
                }
                await _this.$api.requestMap(`exampapertopic`, 'submitQuestions', params);
                uni.navigateBack({
                    delta: 1
                })

            },
        }
    }
</script>

<style>
    .box {
        background-color: #f3f3f3;
        height: 100vh;
        padding-top: 15rpx;
    }

    .datilist {
        background-color: #fff;
        height: 180rpx;
        width: 690rpx;
        margin: 0 auto;
        margin-top: 30rpx;
        box-shadow: 0px 0px 20rpx #acacac;
        display: flex;
        /* align-items: center; */
        flex-direction: column;
        border-radius: 15rpx;
    }

    .exam {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .datilist-List {
        display: flex;
        align-items: center;
        margin-left: 15rpx;
        margin-right: 15rpx;
    }


    .datidata {
        width: 690rpx;
        height: 70vh;
        box-shadow: 0px 0px 20rpx #acacac;
        margin: 0 auto;
        margin-top: 20rpx;
        border-radius: 15rpx;
        background-color: #fff;
    }

    .swiper {
        width: 100%;
        height: 100%;
        padding-top: 20rpx;
    }

    .swiper-item {}

    .swiper-text {
        width: 660rpx;
        height: auto;
        margin: 0 auto;
        line-height: 50rpx;
        /* margin-bottom: 20rpx; */
        /* border-bottom: 1px solid red; */
    }

    .swiper-list {
        width: 660rpx;
        height: 100rpx;
        /* display: flex;
        align-items: center; */
        margin: 0 auto;
        margin-top: 10rpx;
        /* border-bottom: 1rpx solid red; */
    }

    /* .swiper-list:active {
        color: rgb(142, 222, 233)
    } */

    .submit {
        width: 690rpx;
        height: 120rpx;
        margin: 0 auto;
        position: fixed;
        bottom: 0;
        left: 30rpx;
        display: flex;
        align-items: center;
        justify-content: space-around;
    }

    .submit-item {
        width: 300rpx;
        height: 100rpx;
        text-align: center;
        line-height: 100rpx;
        color: #fff;
        background-color: #71d27f;
        border-radius: 50rpx;
    }

    .topic {
        font-size: 34rpx;
        height: 100rpx;
        overflow-x: hidden;
        overflow-y: scroll;
        background-color: #f1eded;
        border-radius: 15rpx;
    }

    /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
    .topic::-webkit-scrollbar {
        width: 6px;
        height: 6px;
        background-color: #ffffff;
    }

    /*定义滚动条轨道 内阴影+圆角*/
    .topic::-webkit-scrollbar-track {
        -webkit-box-shadow: inset 0 0 10px #f1eded;
        border-radius: 10px;
        background-color: #f1eded;
    }

    /*定义滑块 内阴影+圆角*/
    .topic::-webkit-scrollbar-thumb {
        border-radius: 10px;
        -webkit-box-shadow: inset 0 0 10px rgba(0, 0, 0, .3);
        background-color: #000;
    }

    .topic::-webkit-scrollbar {
        display: block;
    }
</style>
