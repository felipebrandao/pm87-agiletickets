package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {
	
	CINEMA {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			BigDecimal preco = sessao.getPreco();
			preco = sessao.ajustaPrecoPorQuantidadeDisponivel(preco, 0.05, 0.10);
			return preco;
		}
	}, SHOW {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			BigDecimal preco = sessao.getPreco();
			preco = sessao.ajustaPrecoPorQuantidadeDisponivel(preco, 0.05, 0.10);
			return preco;
		}
	}, TEATRO {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return sessao.getPreco();
		}
	}, BALLET {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			BigDecimal preco = sessao.getPreco();
			preco = sessao.ajustaPrecoPorQuantidadeDisponivel(preco, 0.50, 0.20);
			preco = sessao.ajustaPrecoPorDuracao(preco);
			return preco;
		}
	}, ORQUESTRA {
		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			BigDecimal preco = sessao.getPreco();
			preco = sessao.ajustaPrecoPorQuantidadeDisponivel(preco, 0.50, 0.20);
			preco = sessao.ajustaPrecoPorDuracao(preco);
			return preco;
		}
	};
	
	public abstract BigDecimal calculaPreco(Sessao sessao);
}
