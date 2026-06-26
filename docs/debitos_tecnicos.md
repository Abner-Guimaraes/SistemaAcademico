# Débitos Técnicos

Este documento registra decisões técnicas ou implementações que foram adiadas no projeto. A gestão destes débitos é essencial para manter a qualidade e o progresso do desenvolvimento.

## Regras para Débitos Técnicos

Em alguns momentos, por decisão do programador, vamos deixar alguns débitos. Isso pode acontecer por duas razões principais:

1. **Para o programador estudar:** É necessário um tempo para entender melhor a tecnologia, o padrão de projeto ou os requisitos antes de decidir se (ou como) deve ser implementado.
2. **Implementar depois:** O programador sabe como resolver ou implementar, mas acha melhor fazer isso posteriormente devido a prioridades atuais. **Atenção:** Sempre que o programador optar por implementar depois, ele deve dar a justificativa e ela deve ser registrada neste documento.

---

## Registro de Débitos

| Data | Débito Técnico / Recurso Adiável | Categoria (Estudo / Depois) | Justificativa / Motivo | Status |
|------|----------------------------------|-----------------------------|------------------------|--------|
| 11/06/2026 | Auditoria / Logging (US-2363 AC8) | Depois | Ferramentas de log previstas na arquitetura (TUS-2390) ainda não foram configuradas. | Pendente |
| 11/06/2026 | Uso de Scanner diretamente no Main | Depois | Abordagem provisória para entrada de dados. O fluxo será evoluído com a criação do menu de linha de comando na US-2364. | Resolvido (US-2364) |
| 26/06/2026 | Autenticação Real e Perfis | Depois | O sistema usa strings para login simulado. Será resolvido na US-2366. | Pendente |
