<template>
    <mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
        <view>
            <view class="mistakes" v-for="(item,index) in this.list">
                <view class="head">
                    {{index+1}}、（{{item.examquestionValue}}）
                    <view class="timu">
                        {{item.examquestionName}}
                    </view>

                </view>
                <view class="option">
                    <view v-for="(io) in JSON.parse(item.examquestionOptions)" v-bind:key="io.code">
                        {{io.code}}、{{io.text}}
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
                CustomBar: '0'
            };
        },
        onShow() {
            this.hasNext = true
            // 重新加载数据
            if (this.mescroll) this.mescroll.resetUpScroll()
        },
        onLoad(options) {
            this.hasNext = true
            this.userid = options.userid
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
                let res = await this.$api.list(`examrewrongquestion`, {
                    page: mescroll.num,
                    limit: mescroll.size,
                });
                // 如果是第一页数据置空
                if (mescroll.num == 1) this.list = [];
                this.list = this.list.concat(res.data.list);
                if (res.data.list.length == 0) this.hasNext = false;
                mescroll.endSuccess(mescroll.size, this.hasNext);
            }
        }
    };
</script>

<style>
    .mistakes{
        width: 690rpx;
        height: auto;
        border-radius: 15rpx;
        background-color: #fff;
        margin: 15rpx auto;
        padding-bottom: 15rpx;
        box-shadow: 0 0 20rpx 0 #acacb4 ;
    }

    .head{
        width: 96%;
        height: auto;
        margin: 15rpx;
        padding-top: 15rpx;
    }

    .option{
        width: 80%;
        height: auto;
        margin: 15rpx;
        margin-left: 30rpx;

    }

    .analysis{
        height: 300rpx;
        display: flex;
        flex-direction: column;
        margin: 15rpx;
        border-radius: 15rpx;
        padding-top: 15rpx;
        padding-bottom: 15rpx;
        padding-left: 15rpx;
        overflow-x: hidden;
        overflow-y: scroll;
        background-color: #f1eded;
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
        height: 120rpx;
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

