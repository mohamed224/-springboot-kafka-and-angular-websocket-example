import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MessageStreamComponent } from './message-stream/message-stream.component';
import { InjectableRxStompConfig, RxStompService, rxStompServiceFactory } from '@stomp/ng2-stompjs';
import { myRxStompConfig } from './rx-stomp.config';
import {HttpClientModule} from '@angular/common/http';
import { RouterModule } from '@angular/router';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";


@NgModule({
  declarations: [
    AppComponent,
    MessageStreamComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule
  
  ],
  providers: [
    {
       provide: InjectableRxStompConfig,
       useValue: myRxStompConfig
    },
    {
       provide: RxStompService,
       useFactory: rxStompServiceFactory,
       deps: [InjectableRxStompConfig]
    }
 ],
  bootstrap: [AppComponent]
})
export class AppModule { }
