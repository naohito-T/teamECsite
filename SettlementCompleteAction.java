package com.internousdev.orion.action;
//決算完了ページ
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.orion.dao.CartInfoDAO;
import com.internousdev.orion.dao.PurchaseHistoryInfoDAO;
import com.internousdev.orion.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementCompleteAction extends ActionSupport implements SessionAware {

	private int id;
	private Map<String,Object> session; //←を実装する事によりsessionのﾌｨｰﾙﾄﾞへstrutsが自動的にHttpSessionの内容をMapの値で格納してくれる*ｲﾝﾀｰﾌｪｰｽ実装する必要。

	public String execute(){
		if(session.isEmpty()){ //sessionが空なのか確認している*Tomcatのﾃﾞﾌｫﾙﾄは30分
			return "sessionTimeout";
		}
		String result =  ERROR;

		String userId = String.valueOf(session.get("loginUserId"));//String型に変換。toStringは変換の値がnullの場合NullPointerExceptionで落ちる。
//valueOf⇒変換したい値がnullであってもNullPointerExceptionを発生させたくない時に使用

		@SuppressWarnings("unchecked")//ｷｬｽﾄをすると仕様上ﾁｪｯｸが入るためｻｰﾊﾞｰにﾁｪｯｸをしないよう伝える。
		List<CartInfoDTO> cartInfoDTOList = (List<CartInfoDTO>) session.get("cartInfoDTOList");//sessionに格納されているcartInfoDTOListを取り出しList型の変数へ格納。
																							//sessionｽｺｰﾌﾟから取り出すため型変換(ｷｬｽﾄ)が必要。格納する事でObject型になっている為
		//DBの購入履歴情報ﾃｰﾌﾞﾙに商品ごとの決済情報を登録する
		PurchaseHistoryInfoDAO purchaseHistoryInfoDAO = new PurchaseHistoryInfoDAO();
		int count = 0;
		for(CartInfoDTO dto: cartInfoDTOList){ //拡張for文を使用し配列の数だけcount
			count += purchaseHistoryInfoDAO.regist(
				userId,
				dto.getProductId(),
				dto.getProductCount(),
				id,
				dto.getPrice()
			);
		}
		//登録できた場合はﾕｰｻﾞｰに紐づいているDBのｶｰﾄ情報ﾃｰﾌﾞﾙの情報を削除する。

		if(count > 0) {
			CartInfoDAO cartInfoDAO = new CartInfoDAO();
			count = cartInfoDAO.deleteAllCartInfo(String.valueOf(session.get("loginUserId")));//String型に変換し河原田君へ渡す
			if(count > 0) {
				result = SUCCESS; //削除できた場合は決算完了ﾍﾟｰｼﾞへ遷移する。
			}
		}
		return result;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
