import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MessageStreamComponent } from './message-stream/message-stream.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: MessageStreamComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
