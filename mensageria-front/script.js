// Função para alternar entre categorias
function alternarCategoria(categoriaId) {
  // Esconde todas as seções de produtos
  document.querySelectorAll(".categoria-produtos").forEach((secao) => {
    secao.classList.remove("active");
  });

  // Mostra apenas a seção selecionada
  document.getElementById(categoriaId).classList.add("active");

  // Atualiza o botão ativo no menu
  document.querySelectorAll(".categoria-btn").forEach((btn) => {
    btn.classList.remove("active");
  });
  document
    .querySelector(`.categoria-btn[data-categoria="${categoriaId}"]`)
    .classList.add("active");
}

// Adiciona eventos aos botões do menu
document.querySelectorAll(".categoria-btn").forEach((btn) => {
  btn.addEventListener("click", () => {
    alternarCategoria(btn.dataset.categoria);
  });
});

// Seu código JavaScript existente
document
  .getElementById("pedido-form")
  .addEventListener("submit", async function (event) {
    event.preventDefault();

    const checkboxes = document.querySelectorAll('input[name="item"]:checked');
    const items = Array.from(checkboxes).map((cb) => cb.value);

    if (items.length === 0) {
      alert("Selecione pelo menos um item!");
      return;
    }

    const pedido = { items };

    try {
      const resposta = await fetch("http://localhost:8081/orders", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pedido),
      });

      const json = await resposta.json();
      document.getElementById("resposta").textContent = JSON.stringify(
        json,
        null,
        2
      );
    } catch (err) {
      document.getElementById("resposta").textContent = "Erro: " + err.message;
    }
  });

const socket = new SockJS("http://localhost:8083/ws"); // porta do Notification-Service
const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
  stompClient.subscribe("/topic/notificacoes", (message) => {
    const body = JSON.parse(message.body);
    mostrarToast(
      body.success
        ? `✅ Pedido ${body.orderId} confirmado!`
        : `❌ Pedido ${body.orderId} falhou: ${body.message}`
    );
  });
});

function mostrarToast(msg) {
  const toast = document.createElement("div");
  toast.className = "toast";
  toast.textContent = msg;
  document.body.appendChild(toast);
  setTimeout(() => toast.remove(), 5000);
}

// Função para calcular o total
function calcularTotal() {
  const checkboxes = document.querySelectorAll(
    'input[type="checkbox"]:checked'
  );
  let total = 0;

  checkboxes.forEach((checkbox) => {
    total += parseFloat(checkbox.dataset.preco);
  });

  document.getElementById("total").textContent = `R$ ${total
    .toFixed(2)
    .replace(".", ",")}`;
}

// Adiciona evento de change a todos os checkboxes
document.querySelectorAll('input[type="checkbox"]').forEach((checkbox) => {
  checkbox.addEventListener("change", calcularTotal);
});

// Inicializa o total
calcularTotal();

// Função para resetar a seleção
function resetarSelecao() {
  // Desmarca todos os checkboxes
  document.querySelectorAll('input[type="checkbox"]').forEach((checkbox) => {
    checkbox.checked = false;
  });

  // Atualiza o total
  calcularTotal();

  // Mostra feedback visual
  mostrarToast("Seleção limpa com sucesso!");
}

// Adiciona evento ao botão de resetar
document
  .getElementById("resetar-btn")
  .addEventListener("click", resetarSelecao);

  