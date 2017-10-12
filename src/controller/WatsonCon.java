package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.Entity;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public class WatsonCon {
	private static WatsonCon watson = new WatsonCon();

	private String username = "e87f73f8-5813-4ffc-b825-cd5450cad5db";
	private String password = "aY6zw7bRhCWR";
	private String workspaceId = "04aa246e-af59-424c-9f85-1be238e99baf";
	private Map<String, Object> context = new HashMap<String, Object>();

	private Map<String, Object> OrderDetail = new HashMap<String, Object>();
	private Map<Integer, List> price = new HashMap<Integer, List>();
	private String dialognodevalue = "";
	public Boolean orderEnd = false;

	private List<String> vegeList = new ArrayList<String>(
			Arrays.asList("LETTUCE", "TOMATOES", "CUCUMBERS", "PEPPERS", "ONIONS", "PICKLES", "OLIVES", "JALAPEINOS"));

	private List<String> classics = Arrays.asList("ham", "tuna", "seafood", "veggie", "egg mayo");
	private List<String> favorites = Arrays.asList("blt", "meatball", "italian bmt", "turkey");
	private List<String> signature = Arrays.asList("spicy italian", "chikenteriyaki", "turkey bacon", "subwayclub");
	private List<String> premium = Arrays.asList("roasted chicken", "roastbeef", "subwaymelt", "steak and cheese",
			"chicken bacon ranch", "turkey bacon avocado");

	public static WatsonCon getInstance() {
		return watson;
	}

	private WatsonCon() {
		price.put(4500, classics);
		price.put(4900, favorites);
		price.put(5300, signature);
		price.put(5800, premium);
	}

	public String getDialognodevalue() {
		return dialognodevalue;
	}

	public void setDialognodevalue(String dialognodevalue) {
		this.dialognodevalue = dialognodevalue;
	}

	public String startConversation(String input) {
		WatsonCon watson = WatsonCon.getInstance();
		MessageResponse response = null;
		StringBuffer responseStr = new StringBuffer();

		System.out.println("speak to watson : " + input);
		try {
			response = watson.conversationAPI(input, context);
			List<String> watsonSay = response.getText();

			if (response != null) {
				System.out.println(response.toString());
				System.out.println("Watson Response: " + watsonSay);

				if (watsonSay.size() > 0) {

					if (watsonSay.get(0).length() == 0) {
						responseStr.append("Please say order again");
					} else {
						System.out.println("watsonSay.size() " + watsonSay.size());
						if (watsonSay.size() == 1) {
							responseStr.append(watsonSay.get(0));
						} else if (watsonSay.size() > 1) {
							responseStr.append(watsonSay.get(watsonSay.size() - 1));
						}
					}

				}
			}

			context = response.getContext();
			JSONObject jsonObject = new JSONObject(context);
			System.out.println(context);

			JSONObject system = (JSONObject) jsonObject.get("system");
			JSONArray dialog_stack = (JSONArray) system.get("dialog_stack");
			JSONObject dialog_node = (JSONObject) dialog_stack.getJSONObject(0);
			dialognodevalue = dialog_node.getString("dialog_node");

			if (!system.isNull("branch_exited_reason")) {
				String extited = (String) system.get("branch_exited_reason");
				System.out.println("extited " + extited);
				if (extited.equals("completed")) {
					orderEnd = true;
				}
				System.out.println("orderEnd " + orderEnd);
			}

			System.out.println("dialog node : " + dialognodevalue);

			if ("root".equals(dialognodevalue)) {
				System.out.println("root");
				OrderDetail = new HashMap<String, Object>();
				watson.orderEnd = false;

			} else if ("OrderStart".equals(dialognodevalue)) {
				System.out.println("order start");
				OrderDetail = new HashMap<String, Object>();
				watson.orderEnd = false;
				
			} else if ("Menu".equals(dialognodevalue)) {
				List<Entity> entity = response.getEntities();
				System.out.println("getEntities " + entity.size());

				for (int i = 0; i < entity.size(); i++) {
					Entity e = entity.get(i);
					if (e.getEntity().equals("menu")) {
						System.out.println("Order menu: " + e.getValue());
						watson.OrderDetail.put(e.getEntity(), e.getValue().toUpperCase());

						for (Integer key : watson.price.keySet()) {
							List<String> menuTmp = watson.price.get(key);
							System.out.println(menuTmp);
							if (menuTmp.contains(e.getValue().toLowerCase())) {
								watson.OrderDetail.put("price", key);
								System.out.println("price : " + key);
								break;
							}
						}
					}
				}

			} else if ("Bread".equals(dialognodevalue)) {
				List<Entity> entity = response.getEntities();
				System.out.println("getEntities " + entity.size());

				for (int i = 0; i < entity.size(); i++) {
					Entity e = entity.get(i);
					if (e.getEntity().equals("bread")) {
						System.out.println("Order bread: " + e.getValue());
						watson.OrderDetail.put(e.getEntity(), e.getValue().toUpperCase());
					}
				}

			} else if ("Vege".equals(dialognodevalue)) {
				List<Entity> entity = response.getEntities();
				System.out.println("getEntities " + entity.size());

				for (int i = 0; i < entity.size(); i++) {
					Entity e = entity.get(i);
					if (e.getEntity().equals("vegetable")) {
						System.out.println("Order vegetable: " + e.getValue());
						watson.vegeList.remove(e.getValue().toUpperCase());
						System.out.println("vegeList : " + watson.vegeList.toString());
					}
				}
				int vegeLen = watson.vegeList.toString().length() - 1;
				String veges = watson.vegeList.toString().substring(1, vegeLen);
				watson.OrderDetail.put("vegetable", veges);
				System.out.println("vegetable : " + veges);

			} else if ("Source".equals(dialognodevalue)) {
				List<Entity> entity = response.getEntities();
				System.out.println("getEntities " + entity.size());

				for (int i = 0; i < entity.size(); i++) {
					Entity e = entity.get(i);
					if (e.getEntity().equals("source")) {
						System.out.println("Order source: " + e.getValue());
						watson.OrderDetail.put(e.getEntity(), e.getValue().toUpperCase());
					}
				}

			} else if ("Cookie".equals(dialognodevalue)) {
				List<Entity> entity = response.getEntities();
				System.out.println("getEntities " + entity.size());

				for (int i = 0; i < entity.size(); i++) {
					Entity e = entity.get(i);
					if (e.getEntity().equals("cookie")) {
						System.out.println("Order cookie: " + e.getValue());
						watson.OrderDetail.put(e.getEntity(), e.getValue().toUpperCase());

						Integer priceTmp = (Integer) watson.OrderDetail.get("price");
						watson.OrderDetail.replace("price", priceTmp + 1000);
					}
				}

			} else if ("Drink".equals(dialognodevalue)) {
				List<Entity> entity = response.getEntities();
				System.out.println("getEntities " + entity.size());

				for (int i = 0; i < entity.size(); i++) {
					Entity e = entity.get(i);
					if (e.getEntity().equals("drink")) {
						System.out.println("Order drink: " + e.getValue());
						watson.OrderDetail.put(e.getEntity(), e.getValue().toUpperCase());

						Integer priceTmp = (Integer) watson.OrderDetail.get("price");
						watson.OrderDetail.replace("price", priceTmp + 1500);
					}
				}

				orderConfirm(responseStr);
				orderEnd = true;
			} else if ("DONE".equals(dialognodevalue) || "EndOrderToDONE".equals(dialognodevalue)) {
				System.out.println("orderEnd " + orderEnd);
				if (!orderEnd) {
					orderConfirm(responseStr);
					System.out.println("Order END");
				}
			}

			System.out.println("-----------------");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return responseStr.toString();
	}

	private StringBuffer orderConfirm(StringBuffer responseStr) {
		responseStr.setLength(0);
		responseStr.append(" ★★★★★★ ORDER CONFIRMATION ★★★★★★ <br><br>");
		for (String key : watson.OrderDetail.keySet()) {
			if (key.equals("price"))
				continue;
			responseStr.append(" - " + key.toUpperCase() + " : " + watson.OrderDetail.get(key) + "<br/><br/>");
		}
		responseStr.append(" - PRICE : " + watson.OrderDetail.get("price") + "<br/><br/>");
		responseStr.append("Are you sure you ordered these?");
		return responseStr;
	}

	public MessageResponse conversationAPI(String input, Map<String, Object> context) {
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
		service.setUsernameAndPassword(username, password);

		MessageRequest newMessage = new MessageRequest.Builder().inputText(input).context(context).build();
		MessageResponse response = service.message(workspaceId, newMessage).execute();
		return response;
	}

}