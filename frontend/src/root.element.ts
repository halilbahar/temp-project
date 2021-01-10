import {
  css,
  customElement,
  html,
  LitElement,
  TemplateResult
} from 'lit-element';
import { Match } from 'navigo';
import { NavigationService } from './services/navigation.service';

import './pages/home.element';
import { globalStyle } from './styles/global';

@customElement('app-root')
export class RootElement extends LitElement {
  outlet: TemplateResult | null = null;

  constructor() {
    super();

    const navigationSerice = NavigationService.getInstance();

    navigationSerice.router
      .on('/', () => {
        this.outlet = html`<app-home></app-home>`;
      })
      .on('/game/:roomId', (match: Match) => {
        const data = match.data as { [key: string]: string };
        this.outlet = html`<app-game roomId="${data.roomId}"></app-game>`;
      })
      .resolve();
  }

  static styles = [
    globalStyle,
    css`
      :host {
        display: block;
        height: 100vh;
        width: 100vw;
        background-color: #fafafa;
      }
    `
  ];

  render() {
    return html`${this.outlet}`;
  }
}
