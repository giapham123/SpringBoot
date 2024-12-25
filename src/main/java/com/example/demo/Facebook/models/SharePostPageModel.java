package com.example.demo.Facebook.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
@Schema(description = "Auto share post to group", example = """
{
    "idPost":"1103062378494007",
    "pageName":"gp.farm47",
    "groupName":[
            "Hội Bà Bầu",
            "Tôi là dân Sài Gòn TP.Hồ Chí Minh",
            "NHÓM HỌP CHỢ TP.HCM",
            "GIỎ QUÀ TẾT 2025",
            "CHỢ ĐẦU MỐI HẠT DẺ",
            "Chợ Sài Gòn Online",
            "MUA BÁN, SỈ LẺ CÁC LOẠI ĐẬU, HẠT TPHCM"   
    ],
    "pageId":"100063707646753"
}
""")
// "Chợ Quận 3 - TP.HCM",
// "Nguồn Hàng Tết Quà Tết 2025 Mới Nhất",
// "Chợ Gò Vấp Online ☑️",
// "Chợ GÒ VẤP online",
// "Chợ Quận 6 - TP.HCM",
// "Chợ Tốt TP.HCM (Sài Gòn) ☑️",
// "CHỢ ĐẦU MỐI - SỈ,LẺ ĐỒ ĂN VẶT",
// "Nguồn Hàng Bánh Kẹo Học Sinh, Tạp Hóa, Hạt Sấy Khô",
// "CHỢ ĐẦU MỐI NÔNG SẢN TP.HCM",
// "Chợ Tân Bình - TP.HCM",
// "Chợ Tốt Sài Gòn Mua Và Bán",
// "CHỢ SỈ ĐỒ TRANG TRÍ TẾT 2024",
// "Sỉ - Lẻ Quà Tết - Phụ Kiện Quà Tết",
// "Chợ Đêm Hạnh Thông Tây Gò Vấp."
// "Chợ Tốt TP.HCM ✅.",
// "GÓC PASS ĐỒ - THANH LÝ ĐỒ MẸ VÀ BÉ",
// "CHUYÊN SỈ BÁNH - KẸO - MỨT TẾT 2025",
// "Chợ Online TP Hồ Chí Minh",
// "Chợ online TpHCM",
// "Giỏ Quà Tết 2025",
// "Sỉ Lẻ Bánh Kẹo Tết",
// "NGUỒN CUNG CẤP BÁNH TRUNG THU VÀ HỘP TRUNG THU 2024",
// "Chợ Tốt TP.Hcm (SÀI GÒN) ✅.",
// "Chuyên Sỉ Bánh Mứt Tết - Hộp Quà Tết 2025 ✅",
// "Chợ Online TPHCM",
// "CHỢ ONLINE SÀI GÒN - TP.HCM",
// "CHỢ ONLINE TPHCM",
// "Ăn Vặt Nguồn Sỉ Giá Rẻ✅",
// "Chợ Phú Nhuận - TP.HCM",
// "Chợ Tốt Sài Gòn",
// "Sỉ Lẻ Giỏ Quà Tết 2025",
// "Chợ Trực Tuyến TP.HCM - Rao Vặt Miễn Phí",
// "Hội các mẹ bầu thông thái❤️",
// "Review Buôn Mê Thuột",
// "CHỢ SÀI GÒN",
// "CHỢ SỈ HẠT DẺ CƯỜI, ÓC CHÓ, HẠNH NHÂN, MACCA, HẠT ĐIỀU, HẠT DƯA, HẠT BÍ HCM",
// "Chợ Quận 7 - TP.HCM",
// "NHÀ ĐẤT GÒ VẤP",
// "NGUỒN HÀNG SỈ LẺ phân phối bia rượu nước ngọt BÁNH KẸO",
// "Chợ Quận 11 - TP.HCM",
// "Quà Tết - Giỏ Quà Tết 2025",
// "Chợ Quận 1 - TP.HCM",
// "Tôi Là Dân Gò Vấp",
// "CHỢ SỈ,Hạt dẻ, ÓC CHÓ, TÁO ĐỎ, HẠT BÍ, NHO KHÔ, HƯỚNG DƯƠNG,Ô MAI...",
// "CHỢ SỈ ĐĂK LĂK (HẠT DƯA, HẠT BÍ, MẮC CA, HƯỚNG DƯƠNG, ÓC CHÓ, HẠT ĐIỀU...)",
// "Chợ Gò Vấp - TP.HCM",
// "Chợ Quận 5 - TP.HCM",
// "NHÓM CHỢ ONLINE SÀI GÒN",
// "Sỉ các loại Hạt",
// "Thị trường hạt điều",
// "Mẹ Bầu Cùng Nhau Chia Sẻ Kinh Nghiệm Mang Thai - Sinh Con",
// "Chia Sẻ Kinh Nghiệm Bán Hàng Shopee Cho Người Mới",
// "Chợ Online ✅",
// "Hạt Điều Bình Phước 100%",
// "Chợ Đầu Mối Hạt Dinh Dưỡng Toàn Quốc - Điều, Macca, Óc Chó, Hạnh Nhân, Dẻ..",
// "CHỢ ONLINE GÒ VẤP TP.HCM"
public class SharePostPageModel extends CommonModel{
    private String idPost;
    private String pageName;
    private int scrollNumbers;
    private List<String> groupName =new ArrayList<>();

    public int getScrollNumbers() {
        return scrollNumbers;
    }

    public void setScrollNumbers(int scrollNumbers) {
        this.scrollNumbers = scrollNumbers;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public List<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }
}
