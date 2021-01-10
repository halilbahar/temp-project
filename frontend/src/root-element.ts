import { customElement, html, LitElement } from 'lit-element';

@customElement('app-root')
export class RootElement extends LitElement {
  render() {
    return html`<p>Hello World</p>`;
  }
}
