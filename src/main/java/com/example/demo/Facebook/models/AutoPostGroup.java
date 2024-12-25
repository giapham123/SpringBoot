package com.example.demo.Facebook.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Auto post to group", example = """
{ 
"image":"/Users/giapham/Downloads/468504563_1081751520625093_7010576151357468674_n.jpg,/Users/giapham/Downloads/468522624_1082556560544589_5403689486128218088_n.jpg,/Users/giapham/Downloads/468755093_1082556557211256_8240101564870615168_n.jpg",
"groupId":"199333313561496,830661183681045",
"content":"Hạt điều rang muối nhà em – chất lượng chuẩn chỉnh từng hạt!\\n🌟 Hạt điều được rang thơm lừng, vị béo ngậy, ăn hạt nào chắc hạt đó. Mỗi sản phẩm đều mang đậm hương vị tự nhiên, đảm bảo làm hài lòng mọi thực khách khó tính nhất.\\n----------------------------------------------------------------\\n🚚 Giao hàng tận nơi trên Toàn Quốc\\n📍 Địa chỉ:\\nKrông Ana - Đắk Lắk\\nGò Vấp - TP. HCM\\n🌐 Website: https://gpfarm47.netlify.app\\n----------------------------------------------------------------\\n#thucphamsach #thiennhien #hatdieu #điều #hạtdinhdưỡng #hatdinhduong #nongsandaklak #nongsan #nongsansach #daklak47"
}
""")
// "groupId":"1877495595803026"
// "groupId":"1877495595803026,1030714580276558,2069787837062474686742,1758207627695661"
// "groupId":"1908251769308065,3733659170004157,6631481936873424,6219832564737018"
// "groupId":"1289214881148136,289897402388525,govap.market,327349018787679"
// "groupId":"134788135130559,HanhThongTayGoVap,1084352688350377"
// "groupId":"2540921346165870,833201633450444,1584494925206866,213274388153369"
public class AutoPostGroup extends CommonModel {
    private String content;

    private String groupId;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
