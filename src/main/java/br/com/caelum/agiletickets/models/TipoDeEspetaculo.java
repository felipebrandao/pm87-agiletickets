package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {
	
	CINEMA {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaPrecoDeEspetaculoSimples(sessao);
		}
	}, SHOW {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaPrecoDeEspetaculoSimples(sessao);
		}
	}, TEATRO {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return sessao.getPreco();
		}
	}, BALLET {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaParaEspetaculoChique(sessao);
		}

		
	}, ORQUESTRA {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return calculaParaEspetaculoChique(sessao);
		}
	};
	
	public abstract BigDecimal calculaPreco(Sessao sessao);
	
	private static BigDecimal calculaParaEspetaculoChique(Sessao sessao) {
		BigDecimal preco = sessao.getPreco();
		preco = sessao.ajustaPrecoPorQuantidadeDisponivel(preco, 0.50, 0.20);
		preco = sessao.ajustaPrecoPorDuracao(preco);
		return preco;
	}

	private static BigDecimal calculaPrecoDeEspetaculoSimples(Sessao sessao) {
		BigDecimal preco = sessao.getPreco();
		preco = sessao.ajustaPrecoPorQuantidadeDisponivel(preco, 0.05, 0.10);
		return preco;
	}
}
