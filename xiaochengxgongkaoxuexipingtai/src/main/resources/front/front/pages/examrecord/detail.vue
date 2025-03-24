<template>
    <mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
        <view class="box">
            <!-- 答题试卷区域 -->
            <view class="datilist">
                <view class="" style="height: 100rpx;text-align: center;line-height: 90rpx;font-size: 40rpx;">
                    {{headDate.exampaperName}}
                </view>
                <view class="exam">
                    <view class="datilist-List">
                        <!-- <uni-icons type="notification" size='30'></uni-icons>
                            <text> 00:00 :05</text> -->
                        总分数：{{headDate.exampaperMyscore}}
                    </view>
                    <view class="datilist-text">
                        {{headDate.totalScore}}分
                    </view>
                </view>
            </view>
            <!-- 答题区域 -->
            <view class="datidata" v-for="(item,index) in list" v-bind:key="index">
                <view class="swiper">
                    <view>
                        <view class="swiper-item">
                            <view class="swiper-text">
                                <view class="" style="display: flex;justify-content: space-between;">

                                    <text>{{item.examquestionValue}}</text>
                                    <view class="datilist-text">
                                        <text>{{index+1}}</text>/
                                        <text>{{list.length}}</text>
                                    </view>
                                </view>
                                <view class="timu">
                                    <text>{{index+1}}、</text>
                                    <text>{{item.examquestionName}}</text>
                                </view>

                            </view>
                            <view class="swiper-list ">
                                <view class="swiper-list" v-if="item.examquestionTypes != 4">
                                    <!--  class="options" -->
                                    <view v-for="(io) in JSON.parse(item.examquestionOptions)" v-bind:key="io.code">
                                        {{io.code}}、{{io.text}}
                                    </view>
                                </view>
                            </view>
                            <view class="analysis">
                                <text style="color: green;">回答：{{item.examredetailsMyanswer}}</text>
                                <text style="margin-top: 15rpx;
									margin-bottom: 15rpx; color: red;">正确答案：{{item.examquestionAnswer}}</text>
                                <view class="analysis-text">解析：{{item.examquestionAnalysis}}</view>
                            </view>
                        </view>
                    </view>
                </view>
            </view>
        </view>

    </mescroll-uni>
</template>

<script>
    export default {
        data() {
            return {
                list: [],
                mescroll: null, //mescroll实例对象
                downOption: {
                    auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
                },
                upOption: {
                    noMoreSize: 5, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
                    textNoMore: '~ 没有更多了 ~',
                },
                hasNext: true,
                searchForm: {},
                CustomBar: '0',
                //试卷信息
                headDate: {},
            };
        },
        onShow() {
            this.hasNext = true
            // 重新加载数据
            if (this.mescroll) this.mescroll.resetUpScroll()
        },
        onLoad(options) {
            this.hasNext = true
            this.headDate = JSON.parse(options.item)
            // 重新加载数据
            if (this.mescroll) this.mescroll.resetUpScroll()
        },
        methods: {
            // mescroll组件初始化的回调,可获取到mescroll对象
            mescrollInit(mescroll) {
                this.mescroll = mescroll;
            },
            /*下拉刷新的回调 */
            downCallback(mescroll) {
                this.hasNext = true
                // 重置分页参数页数为1
                mescroll.resetUpScroll()
            },
            /*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
            async upCallback(mescroll) {
                let _this = this
                let res = await _this.$api.page(`examredetails`, {
                    page: mescroll.num,
                    limit: mescroll.size,
                    examredetailsUuidNumber: _this.headDate.examrecordUuidNumber,
                });
                // 如果是第一页数据置空
                if (mescroll.num == 1) _this.list = [];
                _this.list = _this.list.concat(res.data.list);
                if (res.data.list.length == 0) _this.hasNext = false;
                //试卷名称
                _this.totalScore = _this.list[0].totalScore;
                mescroll.endSuccess(mescroll.size, _this.hasNext);
            }
        }
    };
</script>

<style>
    .box {
        /* background-color: silver; */
        /* height: 100vh; */
        padding-top: 15rpx;
    }

    .datilist {
        background-color: #fff;
        height: 180rpx;
        width: 690rpx;
        margin: 0 auto;
        margin-top: 20rpx;
        box-shadow: 0rpx 0rpx 15rpx 0rpx #acacb4;
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
    }

    .datilist-text {
        margin-right: 20rpx;
    }

    .datidata {
        width: 690rpx;
        height: auto;
        margin: 0 auto;
        box-shadow: 0rpx 0rpx 20rpx 0rpx #acacb4;
        margin-top: 15rpx;
        padding-bottom: 15rpx;
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
        /* margin-bottom: -10rpx; */
        /* border-bottom: 1px solid red; */
    }

    .swiper-list {
        width: 660rpx;
        height: auto;
        /* display: flex;
        align-items: center; */
        margin: 0 auto;
        margin-left: 30rpx;
        /* margin-top: 10rpx; */
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
        background-color: lightblue;
        border-radius: 50rpx;
    }

    .analysis {
        width: 640rpx;
        height: 300rpx;
        background-color: #f1eded;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        margin-top: 15rpx;
        margin-left: 25rpx;
        border: 1rpx solid #f1eded;
        border-radius: 15rpx;
        padding-top: 15rpx;
        padding-bottom: 15rpx;
        padding-left: 15rpx;
        overflow-x: hidden;
        overflow-y: scroll;
    }

    /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
    .analysis::-webkit-scrollbar {
        width: 6px;
        height: 6px;
        background-color: #ffffff;
    }

    /*定义滚动条轨道 内阴影+圆角*/
    .analysis::-webkit-scrollbar-track {
        -webkit-box-shadow: inset 0 0 10px #f1eded;
        border-radius: 10px;
        background-color: #f1eded;
    }

    /*定义滑块 内阴影+圆角*/
    .analysis::-webkit-scrollbar-thumb {
        border-radius: 10px;
        -webkit-box-shadow: inset 0 0 10px #f1eded;
        background-color: #000;
    }

    .analysis::-webkit-scrollbar {
        display: block;
    }
    .timu{
        height: 100rpx;
        background-color: #f1eded;
        border-radius: 15rpx;
        overflow-x: hidden;
        overflow-y: scroll;
    }

    /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
    .timu::-webkit-scrollbar {
        width: 6px;
        height: 6px;
        background-color: #ffffff;
    }

    /*定义滚动条轨道 内阴影+圆角*/
    .timu::-webkit-scrollbar-track {
        -webkit-box-shadow: inset 0 0 10px #fff;
        border-radius: 10px;
        background-color: #fff;
    }

    /*定义滑块 内阴影+圆角*/
    .timu::-webkit-scrollbar-thumb {
        border-radius: 10px;
        -webkit-box-shadow: inset 0 0 10px rgba(0, 0, 0, .3);
        background-color: #fff;
    }
</style>

