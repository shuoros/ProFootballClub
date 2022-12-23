<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="transfer">
        <h2 class="jh-entity-heading" data-cy="transferDetailsHeading">
          <span v-text="$t('proFootballClubApp.transfer.detail.title')">Transfer</span> {{ transfer.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.transfer.price')">Price</span>
          </dt>
          <dd>
            <span>{{ transfer.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.transfer.password')">Password</span>
          </dt>
          <dd>
            <span>{{ transfer.password }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.transfer.bought')">Bought</span>
          </dt>
          <dd>
            <span>{{ transfer.bought }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.transfer.player')">Player</span>
          </dt>
          <dd>
            <div v-if="transfer.player">
              <router-link :to="{ name: 'PlayerView', params: { playerId: transfer.player.id } }">{{ transfer.player.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="transfer.id" :to="{ name: 'TransferEdit', params: { transferId: transfer.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./transfer-details.component.ts"></script>
