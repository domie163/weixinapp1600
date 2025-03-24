const base = {
    get() {
        return {
            url : "http://localhost:8080/xiaochengxgongkaoxuexipingtai/",
            name: "xiaochengxgongkaoxuexipingtai",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/xiaochengxgongkaoxuexipingtai/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "小程序公考学习平台"
        } 
    }
}
export default base
