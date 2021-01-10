import { css, customElement, html, LitElement } from 'lit-element';
import { Room } from '../models/room.model';
import { ApiService } from '../services/api.service';
import { globalStyle } from '../styles/global';

@customElement('app-home')
export class HomeElement extends LitElement {
  rooms: Room[] = [];

  constructor() {
    super();
    this.refreshRooms();
  }

  static styles = [
    globalStyle,
    css`
      :host {
        display: block;
        height: 100%;
        width: 100%;
        padding: 3% 5% !important;
      }

      .controls {
        display: flex;
      }

      .controls input {
        flex: 1;
      }

      .controls > *:not(:first-child) {
        margin-left: 1rem;
      }
    `
  ];

  render() {
    return html`
      <div class="controls">
        <input />
        <button>Join</button>
        <button>Reload</button>
      </div>

      ${this.rooms.map(item => html`
      <div>
      ${item.id} - ${item.name}
      </div>
      `)}
    `;
  }

  private refreshRooms(): void {
    this.rooms = ApiService.rooms;
  }
}
