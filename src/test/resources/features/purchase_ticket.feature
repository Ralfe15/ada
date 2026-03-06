# language: pt
Funcionalidade: Compra de ingressos
  Como um cliente do EventMaster
  Eu quero comprar ingressos para um evento
  Para que eu possa participar do evento

  Cenário: Compra de ingresso com sucesso via PIX
    Dado que existe um evento "Show de Rock" com 100 ingressos disponíveis ao preço de R$ 50.00
    Quando o cliente "joao@email.com" compra 2 ingressos via "PIX"
    Então o pedido deve ser confirmado com valor total de R$ 100.00
    E o evento deve ter 98 ingressos disponíveis
