import { css, customElement, html, LitElement } from "lit-element";
import { Room } from "../models/room.model";
import { ApiService } from "../services/api.service";

@customElement('app-home')
export class HomeElement extends LitElement {

  rooms: Room[] = [];

  constructor() {
    super();
    this.refreshRooms();
  }

  static styles = css`
    :host {
      display: block;
      height: 100%;
      width: 100%;
    }
  `;

  render() {
    return html``;
  }

  private refreshRooms(): void {
    this.rooms = ApiService.rooms;
  }
}
