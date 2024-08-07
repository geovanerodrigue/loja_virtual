package com.loja;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loja.enums.ApiTokenIntegracao;
import com.loja.model.dto.EmpresaTransporteDTO;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TesteAPIMelhorEnvio {

	public static void main(String[] args) throws Exception {

		
		
		
    //INSERE AS ETIQUETAS DE FRETE		
	/**
		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n    \"service\": 3,\n    \"agency\": 49,\n    \"from\": {\n        \"name\": \"Nome do remetente\",\n        \"phone\": \"53984470102\",\n        \"email\": \"contato@melhorenvio.com.br\",\n        \"document\": \"16571478358\",\n        \"company_document\": \"89794131000100\",\n        \"state_register\": \"123456\",\n        \"address\": \"Endereço do remetente\",\n        \"complement\": \"Complemento\",\n        \"number\": \"1\",\n        \"district\": \"Bairro\",\n        \"city\": \"São Paulo\",\n        \"country_id\": \"BR\",\n        \"postal_code\": \"01002001\",\n        \"note\": \"observação\"\n    },\n    \"to\": {\n        \"name\": \"Nome do destinatário\",\n        \"phone\": \"53984470102\",\n        \"email\": \"contato@melhorenvio.com.br\",\n        \"document\": \"25404918047\",\n        \"company_document\": \"07595604000177\",\n        \"state_register\": \"123456\",\n        \"address\": \"Endereço do destinatário\",\n        \"complement\": \"Complemento\",\n        \"number\": \"2\",\n        \"district\": \"Bairro\",\n        \"city\": \"Porto Alegre\",\n        \"state_abbr\": \"RS\",\n        \"country_id\": \"BR\",\n        \"postal_code\": \"90570020\",\n        \"note\": \"observação\"\n    },\n    \"products\": [\n        {\n            \"name\": \"Papel adesivo para etiquetas 1\",\n            \"quantity\": 3,\n            \"unitary_value\": 100.00\n        },\n        {\n            \"name\": \"Papel adesivo para etiquetas 2\",\n            \"quantity\": 1,\n            \"unitary_value\": 100.00\n        }\n    ],\n    \"volumes\": [\n        {\n            \"height\": 15,\n            \"width\": 20,\n            \"length\": 10,\n            \"weight\": 3.5\n        }\n    ],\n    \"options\": {\n        \"insurance_value\": 10.00,\n        \"receipt\": false,\n        \"own_hand\": false,\n        \"reverse\": false,\n        \"non_commercial\": false,\n        \"invoice\": {\n            \"key\": \"31190307586261000184550010000092481404848162\"\n        },\n        \"platform\": \"Nome da Plataforma\",\n        \"tags\": [\n            {\n                \"tag\": \"Identificação do pedido na plataforma, exemplo: 1000007\",\n                \"url\": \"Link direto para o pedido na plataforma, se possível, caso contrário pode ser passado o valor null\"\n            }\n        ]\n    }\n}");;
		Request request = new Request.Builder()
		  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/cart")
		  .post(body)
		  .addHeader("Accept", "application/json")
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
		  .addHeader("User-Agent", "geovane.net@live.com")
		  .build();

		Response response = client.newCall(request).execute();
	**/	
		
  
  // FAZ A COMPRA DO FRETE PARA A ETIQUETA	
	/**	
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"orders\":[\"9cb46f3a-cb00-4e7c-92cd-36259aa9b9fd\"]}");
		Request request = new Request.Builder()
		  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/checkout")
		  .post(body)
		  .addHeader("Accept", "application/json")
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
		  .addHeader("User-Agent", "geovane.net@live.com")
		  .build();

		Response response = client.newCall(request).execute();
	**/	
		
	
		
     //gera etiquetas
	/**	
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"orders\":[\"9cb46f3a-cb00-4e7c-92cd-36259aa9b9fd\"]}");
		Request request = new Request.Builder()
		  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/generate")
		  .post(body)
		  .addHeader("Accept", "application/json")
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
		  .addHeader("User-Agent", "geovane.net@live.com")
		  .build();

		Response response = client.newCall(request).execute();	
	**/	

		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"mode\":\"\",\"orders\":[\"9cb46f3a-cb00-4e7c-92cd-36259aa9b9fd\"]}");
		Request request = new Request.Builder()
		  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/print")
		  .post(body)
		  .addHeader("Accept", "application/json")
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
		  .addHeader("User-Agent", "geovane.net@live.com")
		  .build();

		Response response = client.newCall(request).execute();
		
			
		
  /**
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\"from\":{\"postal_code\":\"96020360\"},\"to\":{\"postal_code\":\"01018020\"},\"products\":[{\"id\":\"x\",\"width\":11,\"height\":17,\"length\":11,\"weight\":0.3,\"insurance_value\":10.1,\"quantity\":1},{\"id\":\"y\",\"width\":16,\"height\":25,\"length\":11,\"weight\":0.3,\"insurance_value\":55.05,\"quantity\":2},{\"id\":\"z\",\"width\":22,\"height\":30,\"length\":11,\"weight\":1,\"insurance_value\":30,\"quantity\":1}]}");
		Request request = new Request.Builder()
				.url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/calculate")
				.method("POST", body)
				.addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				.addHeader("User-Agent", "geovane.net@live.com")
				.build();

		Response response = client.newCall(request).execute();
		//System.out.println(response.body().string());

		
		  JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
		 
		  Iterator<JsonNode> iterator = jsonNode.iterator();
		 

		  List<EmpresaTransporteDTO> empresaTransporteDTOs = new ArrayList<EmpresaTransporteDTO>();
		 
			while(iterator.hasNext()) {
				JsonNode node = iterator.next();
				
				EmpresaTransporteDTO empresaTransporteDTO = new EmpresaTransporteDTO();
				
				if (node.get("id") != null) {
					empresaTransporteDTO.setId(node.get("id").asText());
				}
				
				if (node.get("name") != null) {
					empresaTransporteDTO.setNome(node.get("name").asText());
				}
				
				if (node.get("price") != null) {
					empresaTransporteDTO.setValor(node.get("price").asText());
				}
				
				if (node.get("company") != null) {
					empresaTransporteDTO.setEmpresa(node.get("company").get("name").asText());
					empresaTransporteDTO.setPicture(node.get("company").get("picture").asText());
				}
				
				if (empresaTransporteDTO.dadosOK()) {
					empresaTransporteDTOs.add(empresaTransporteDTO);
				}
			}
			
			System.out.println(empresaTransporteDTOs);
	**/
		
		
		System.out.println(response.body().string());
		
	}
}
