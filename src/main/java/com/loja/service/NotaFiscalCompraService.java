package com.loja.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.loja.model.dto.ObjetoRelatorioStatusCompra;
import com.loja.model.dto.ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO;
import com.loja.model.dto.ObjetoRequisicaoRelatorioProdutoAlertaEstoque;

@Service
public class NotaFiscalCompraService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	public List<ObjetoRelatorioStatusCompra> relatorioStatusVendaLojaVirtual(ObjetoRelatorioStatusCompra objetoRelatorioStatusCompra) {
		
		List<ObjetoRelatorioStatusCompra> retorno = new ArrayList<ObjetoRelatorioStatusCompra>();
		
		
		String sql = "select p.id as codigoProduto, "
				+ " p.nome as nomeProduto, "
				+ " pf.email as emailCliente, "
				+ " pf.telefone as foneCliente, "
				+ " p.valor_venda as valorVendaProduto, "
				+ " pf.id as codigoCliente,  "
				+ " pf.nome as nomeCliente, "
				+ " p.qtd_estoque as qtdEstoque, "
				+ " cfc.id as codigoVenda, "
				+ " cfc.status_venda_loja_virtual as statusVenda  "
				+ " from vd_cp_loja_virt as cfc "
				+ " inner join item_venda_loja as ntp on ntp.venda_compra_loja_virtu_id = cfc.id "
				+ " inner join produto as p on p.id = ntp.produto_id "
				+ " inner join pessoa_fisica as pf on pf.id = cfc.pessoa_id ";
		
				sql += " where cfc.data_vneda >= '"+objetoRelatorioStatusCompra.getDataInicial()+"' and cfc.data_vneda <= '"+objetoRelatorioStatusCompra.getDataFinal()+"' ";
				
				if(!objetoRelatorioStatusCompra.getNomeProduto().isEmpty()) {
				   sql += " and upper(p.nome) like upper('%"+objetoRelatorioStatusCompra.getNomeProduto()+"%') ";
				}
				
				if(!objetoRelatorioStatusCompra.getStatusVenda().isEmpty()) {
				   sql += " and cfc.status_venda_loja_virtual = ('"+objetoRelatorioStatusCompra.getStatusVenda()+"')";
				}
				
				if(!objetoRelatorioStatusCompra.getNomeCliente().isEmpty()) {
				   sql += " and pf.nome like  '%"+objetoRelatorioStatusCompra.getNomeCliente()+"%' ";
				}
				
		retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ObjetoRelatorioStatusCompra.class));
		
		return retorno;
		
	}
	
	

	public List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO> gerarRelatorioProdCompraNota
	       (ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO objetoRequisicaoRelatorioProdCompraNotaFiscalDTO) {
		
		List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO> retorno = new ArrayList<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO>();
		
		String sql = "select p.id as codigoProduto, p.nome as nomeProduto, "
				+ " p.valor_venda as valorVendaProduto, ntp.quantidade as quantidadeComprada, "
				+ " pj.id as codigoFornecedor, pj.nome as nonmeFornecedor, cfc.data_compra as dataCompra "
				+ " from nota_fiscal_compra as cfc "
				+ " inner join nota_item_produto as ntp on cfc.id = nota_fiscal_compra_id "
				+ " inner join produto as p on p.id = ntp.produto_id "
				+ " inner join pessoa_juridica as pj on pj.id = cfc.pessoa_id where ";
		
		
		sql += " cfc.data_compra >= '" + objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getDataInicial() + "' and ";
		sql += " cfc.data_compra <= '" + objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getDataFinal() + "' ";
		sql += " and cfc.id = " + objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getCodigoNota() + " ";
		
		if(!objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getCodigoNota().isEmpty()) {
			sql += " and cfc.id = " + objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getCodigoNota() + " ";
		}
		
		if(!objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getCodigoProduto().isEmpty()) {
			sql += " and p.id = " + objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getCodigoProduto() + " ";
		}
		
		if(!objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getNomeProduto().isEmpty()) {
			sql += " upper(p.nome) like upper('%" + objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getNomeProduto() + "')";
		}
		
		if(!objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getNomeFornecedor().isEmpty()) {
			sql += " upper(pj.nome) like upper('%" + objetoRequisicaoRelatorioProdCompraNotaFiscalDTO.getNomeFornecedor() + "')";
		}
		
		retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO.class));
		
		return retorno;
	}
	
	public List<ObjetoRequisicaoRelatorioProdutoAlertaEstoque> gerarRelatorioAlertaEstoque
	   (ObjetoRequisicaoRelatorioProdutoAlertaEstoque alertaEstoque) {
		
		List<ObjetoRequisicaoRelatorioProdutoAlertaEstoque> retorno = new ArrayList<ObjetoRequisicaoRelatorioProdutoAlertaEstoque>();
		
		String sql = "select p.id as codigoProduto, p.nome as nomeProduto, "
				+ " p.valor_venda as valorVendaProduto, ntp.quantidade as quantidadeComprada, "
				+ " pj.id as codigoFornecedor, pj.nome as nonmeFornecedor, cfc.data_compra as dataCompra, "
				+ " p.qtd_estoque as qtdEstoque, p.qtd_alerta_estoque as qtdAlertaEstoque "
				+ " from nota_fiscal_compra as cfc "
				+ " inner join nota_item_produto as ntp on cfc.id = nota_fiscal_compra_id "
				+ " inner join produto as p on p.id = ntp.produto_id "
				+ " inner join pessoa_juridica as pj on pj.id = cfc.pessoa_id where ";
		
		
		sql += " cfc.data_compra >= '" + alertaEstoque.getDataInicial() + "' and ";
		sql += " cfc.data_compra <= '" + alertaEstoque.getDataFinal() + "' ";
		sql += " and p.alerta_qtd_estoque = true and p.qtd_estoque <= p.qtd_alerta_estoque ";
		
		if(!alertaEstoque.getCodigoNota().isEmpty()) {
			sql += " and cfc.id = " + alertaEstoque.getCodigoNota() + " ";
		}
		
		if(!alertaEstoque.getCodigoProduto().isEmpty()) {
			sql += " and p.id = " + alertaEstoque.getCodigoProduto() + " ";
		}
		
		if(!alertaEstoque.getNomeProduto().isEmpty()) {
			sql += " upper(p.nome) like upper('%" + alertaEstoque.getNomeProduto() + "')";
		}
		
		if(!alertaEstoque.getNomeFornecedor().isEmpty()) {
			sql += " upper(pj.nome) like upper('%" + alertaEstoque.getNomeFornecedor() + "')";
		}
		
		retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ObjetoRequisicaoRelatorioProdutoAlertaEstoque.class));
		
		return retorno;
		
	}

}
