'use client'

import Link from "next/link";

export default function Page() {
  return (
    <>
      <style jsx global>{`
        :root {
          --bg-light: #FAF9F6;
          --bg-sand: #F3EFE6;
          --accent-sand: #D8C8B0;
          --accent-gold: #C5A059;
          --text-dark: #1A1A1A;
          --text-muted: #66605C;
          --white: #FFFFFF;
          --font-main: 'Segoe UI', -apple-system, BlinkMacSystemFont, Roboto, sans-serif;
        }

        * {
          margin: 0;
          padding: 0;
          box-sizing: border-box;
        }

        body {
          font-family: var(--font-main);
          background-color: var(--bg-light);
          color: var(--text-dark);
          line-height: 1.6;
          position: relative;
          overflow-x: hidden;
        }

        html {
          scroll-behavior: smooth;
        }

        /* ELEMENTOS DE ARTE EM FINE LINE NO FUNDO */
        .botanical-bg {
          position: absolute;
          z-index: 0;
          pointer-events: none;
          opacity: 0.55;
        }

        .botanical-top-left {
          top: 0;
          left: -40px;
          width: 380px;
          height: auto;
        }

        .botanical-middle-right {
          top: 35%;
          right: -50px;
          width: 420px;
          height: auto;
        }

        .botanical-bottom-left {
          bottom: 5%;
          left: -60px;
          width: 400px;
          height: auto;
        }

        /* HEADER & NAVIGATION */
        header {
          background-color: rgba(250, 249, 246, 0.85);
          backdrop-filter: blur(12px);
          position: sticky;
          top: 0;
          z-index: 100;
          border-bottom: 1px solid rgba(216, 200, 176, 0.4);
        }

        .nav-container {
          max-width: 1200px;
          margin: 0 auto;
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 1rem 2rem;
          position: relative;
          z-index: 10;
        }

        .logo {
          display: flex;
          align-items: center;
          gap: 0.85rem;
          text-decoration: none;
          color: var(--text-dark);
        }

        .logo-symbol {
          width: 42px;
          height: 42px;
          background: linear-gradient(135deg, var(--bg-sand) 0%, var(--accent-sand) 100%);
          border-radius: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
        }

        .logo-text-wrapper {
          display: flex;
          flex-direction: column;
        }

        .logo-title {
          font-size: 1.35rem;
          font-weight: 700;
          letter-spacing: 0.05em;
          text-transform: uppercase;
          line-height: 1;
        }

        .logo-subtitle {
          font-size: 0.65rem;
          letter-spacing: 0.25em;
          text-transform: uppercase;
          color: var(--accent-gold);
          font-weight: 600;
          margin-top: 2px;
        }

        /* AUTH BUTTONS */
        .auth-actions {
          display: flex;
          align-items: center;
          gap: 1rem;
        }

        .btn-link {
          color: var(--text-dark);
          text-decoration: none;
          font-weight: 600;
          font-size: 0.95rem;
          padding: 0.6rem 1rem;
          transition: color 0.2s ease;
        }

        .btn-link:hover {
          color: var(--accent-gold);
        }

        .btn {
          display: inline-block;
          padding: 0.75rem 1.5rem;
          border-radius: 50px;
          text-decoration: none;
          font-weight: 600;
          font-size: 0.95rem;
          transition: all 0.3s ease;
          cursor: pointer;
          border: none;
        }

        .btn-primary {
          background-color: var(--text-dark);
          color: var(--bg-light);
        }

        .btn-primary:hover {
          background-color: var(--accent-gold);
          color: var(--white);
          transform: translateY(-1px);
        }

        /* HERO SECTION */
        .hero {
          max-width: 1200px;
          margin: 0 auto;
          padding: 6rem 2rem;
          display: grid;
          grid-template-columns: 1fr 1fr;
          gap: 4rem;
          align-items: center;
          position: relative;
          z-index: 1;
        }

        .hero-content h1 {
          font-size: 3.25rem;
          line-height: 1.15;
          margin-bottom: 1.5rem;
          font-weight: 600;
          letter-spacing: -0.03em;
        }

        .hero-content p {
          font-size: 1.15rem;
          color: var(--text-muted);
          margin-bottom: 2.5rem;
        }

        .hero-card {
          background: rgba(255, 255, 255, 0.85);
          backdrop-filter: blur(10px);
          border-radius: 28px;
          padding: 3.5rem;
          border: 1px solid var(--accent-sand);
          box-shadow: 0 20px 40px rgba(0,0,0,0.03);
        }

        .hero-card h3 {
          font-size: 1.5rem;
          margin-bottom: 0.75rem;
        }

        .hero-card p {
          color: var(--text-muted);
        }

        /* FEATURES SECTION */
        .features {
          background-color: rgba(255, 255, 255, 0.9);
          padding: 6rem 2rem;
          border-top: 1px solid var(--bg-sand);
          border-bottom: 1px solid var(--bg-sand);
          position: relative;
          z-index: 1;
        }

        .features-container {
          max-width: 1200px;
          margin: 0 auto;
        }

        .section-title {
          text-align: center;
          font-size: 2.25rem;
          margin-bottom: 3.5rem;
          font-weight: 600;
          letter-spacing: -0.02em;
        }

        .grid-features {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
          gap: 2rem;
        }

        .feature-card {
          background-color: var(--bg-light);
          padding: 2.5rem;
          border-radius: 20px;
          border: 1px solid var(--bg-sand);
          transition: transform 0.3s ease;
        }

        .feature-card:hover {
          transform: translateY(-4px);
        }

        .feature-card h3 {
          font-size: 1.3rem;
          margin-bottom: 0.75rem;
        }

        .feature-card p {
          color: var(--text-muted);
          font-size: 0.95rem;
        }

        /* ABOUT SECTION */
        .about {
          max-width: 820px;
          margin: 0 auto;
          padding: 6rem 2rem;
          text-align: center;
          position: relative;
          z-index: 1;
        }

        .about h2 {
          font-size: 2.25rem;
          margin-bottom: 1.5rem;
        }

        .about p {
          font-size: 1.2rem;
          color: var(--text-muted);
          line-height: 1.8;
        }

        /* FOOTER */
        footer {
          background-color: var(--text-dark);
          color: var(--bg-light);
          text-align: center;
          padding: 2.5rem 2rem;
          font-size: 0.9rem;
          position: relative;
          z-index: 1;
        }

        @media (max-width: 768px) {
          .hero {
            grid-template-columns: 1fr;
            padding: 3.5rem 1.5rem;
          }
          .hero-content h1 {
            font-size: 2.4rem;
          }
          .auth-actions {
            gap: 0.5rem;
          }
          .btn-link {
            display: none;
          }
          .botanical-bg {
            opacity: 0.35;
            width: 250px;
          }
        }
      `}</style>

      {/* VETORES BOTÂNICOS FINE LINE EM TOM AREIA */}
      {/* Canto Superior Esquerdo - Ramo com Flores */}
      <svg className="botanical-bg botanical-top-left" viewBox="0 0 300 400" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M10 380 C 80 280, 150 180, 180 20" stroke="#D8C8B0" strokeWidth="1.2" strokeLinecap="round" />
        {/* Folha 1 */}
        <path d="M110 240 C 130 220, 160 225, 170 240 C 150 255, 120 255, 110 240 Z" stroke="#D8C8B0" strokeWidth="1" fill="none" />
        <path d="M110 240 C 135 235, 155 238, 170 240" stroke="#D8C8B0" strokeWidth="0.8" />
        {/* Folha 2 */}
        <path d="M60 300 C 40 280, 10 285, 0 300 C 20 315, 50 315, 60 300 Z" stroke="#D8C8B0" strokeWidth="1" fill="none" />
        {/* Flor Fine Line */}
        <g transform="translate(180, 20) rotate(-15)">
          <path d="M0 0 C -15 -25, -25 -15, -10 -5 M0 0 C 15 -25, 25 -15, 10 -5 M0 0 C -25 -10, -20 15, -5 10 M0 0 C 25 -10, 20 15, 5 10 M0 0 C 0 -30, 15 -30, 0 0" stroke="#D8C8B0" strokeWidth="1" fill="none" />
          <circle cx="0" cy="0" r="3" fill="#D8C8B0" />
        </g>
      </svg>

      {/* Centro / Direita - Folhagem Orgânica */}
      <svg className="botanical-bg botanical-middle-right" viewBox="0 0 350 500" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M320 10 C 250 150, 100 280, 20 480" stroke="#D8C8B0" strokeWidth="1.2" strokeLinecap="round" />
        {/* Ramos Laterais com Folhas em Fine Line */}
        <path d="M220 180 C 180 160, 140 170, 130 190 C 160 210, 200 200, 220 180 Z" stroke="#D8C8B0" strokeWidth="1" fill="none" />
        <path d="M160 270 C 210 250, 240 270, 250 290 C 220 305, 180 295, 160 270 Z" stroke="#D8C8B0" strokeWidth="1" fill="none" />
        <path d="M80 380 C 40 350, 10 365, 0 380 C 30 400, 60 395, 80 380 Z" stroke="#D8C8B0" strokeWidth="1" fill="none" />
      </svg>

      {/* Canto Inferior Esquerdo - Flores em Linha Fina */}
      <svg className="botanical-bg botanical-bottom-left" viewBox="0 0 300 350" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M0 350 C 100 250, 120 150, 100 50" stroke="#D8C8B0" strokeWidth="1.2" strokeLinecap="round" />
        <g transform="translate(100, 50)">
          <path d="M0 0 C -20 -30, 20 -30, 0 0" stroke="#D8C8B0" strokeWidth="1" />
          <path d="M0 0 C -30 -20, -30 20, 0 0" stroke="#D8C8B0" strokeWidth="1" />
          <path d="M0 0 C 30 -20, 30 20, 0 0" stroke="#D8C8B0" strokeWidth="1" />
          <path d="M0 0 C -20 30, 20 30, 0 0" stroke="#D8C8B0" strokeWidth="1" />
          <circle cx="0" cy="0" r="2.5" fill="#D8C8B0" />
        </g>
      </svg>

      {/* HEADER */}
      <header>
        <div className="nav-container">
          <a href="#" className="logo">
            <div className="logo-symbol">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M4 8C7 5 11 5 13 8C15 11 19 11 21 8" stroke="#1A1A1A" strokeWidth="1.8" strokeLinecap="round" />
                <path d="M3 13C6 10 10 10 12 13C14 16 18 16 20 13" stroke="#C5A059" strokeWidth="1.8" strokeLinecap="round" />
                <path d="M14 17L18 17C19.1 17 20 16.1 20 15V15C20 13.9 19.1 13 18 13H15" stroke="#1A1A1A" strokeWidth="1.8" strokeLinecap="round" />
                <path d="M16 17L15 21" stroke="#1A1A1A" strokeWidth="1.8" strokeLinecap="round" />
              </svg>
            </div>
            <div className="logo-text-wrapper">
              <span className="logo-title">GLAMOUR</span>
              <span className="logo-subtitle">STUDIO</span>
            </div>
          </a>

          <div className="auth-actions">
            <Link href="/login" className="btn-link">Entrar</Link>

          </div>
        </div>
      </header>

      {/* HERO SECTION */}
      <section className="hero">
        <div className="hero-content">
          <h1>Sua agenda sob controle, seu salão em evolução.</h1>
          <p>O ecossistema de gestão desenvolvido para cabeleireiros otimizarem tempo, organizarem atendimentos e focarem no que realmente importa: a arte de transformar.</p>
          <a href="#recursos" className="btn btn-primary">Conheça a Plataforma</a>
        </div>
        <div className="hero-card">
          <h3>Agenda Inteligente</h3>
          <p>
            Acompanhe a rotina de toda a sua equipe em tempo real, evitando choque de horários e gargalos no atendimento.
          </p>
        </div>
      </section>

      {/* FEATURES */}
      <section className="features" id="recursos">
        <div className="features-container">
          <h2 className="section-title">Pensado para o dia a dia do seu salão</h2>
          <div className="grid-features">
            <div className="feature-card">
              <h3>Gestão de Agenda</h3>
              <p>Organize horários por profissional de forma clara e ágil. Visualize a rotina do salão em um único painel.</p>
            </div>
            <div className="feature-card">
              <h3>Controle de Clientes</h3>
              <p>Mantenha o histórico de atendimentos e preferências de cada cliente sempre à mão para um serviço personalizado.</p>
            </div>
            <div className="feature-card">
              <h3>Serviços & Procedimentos</h3>
              <p>Cadastre o catálogo completo de serviços, durações médias e valores de forma simples e rápida.</p>
            </div>
          </div>
        </div>
      </section>

      {/* ABOUT SECTION */}
      <section className="about">
        <h2>Nossa História</h2>
        <p>
          O <strong>GlamourStudio</strong> nasceu da necessidade de simplificar a rotina dos profissionais da beleza. Sabemos que a rotina por trás das cadeiras requer atenção e agilidade. Por isso, criamos uma solução direta para que cabeleireiros e gestores tenham controle total sobre seus horários, clientes e serviços, garantindo mais tempo livre e uma experiência superior no salão.
        </p>
      </section>

      {/* FOOTER */}
      <footer>
        <p>&copy; 2026 GlamourStudio. Todos os direitos reservados.</p>
      </footer>
    </>
  );
}