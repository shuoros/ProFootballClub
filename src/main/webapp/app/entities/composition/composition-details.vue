<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="composition">
        <h2 class="jh-entity-heading" data-cy="compositionDetailsHeading">
          <span v-text="$t('proFootballClubApp.composition.detail.title')">Composition</span> {{ composition.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.composition.d3fault')">D 3 Fault</span>
          </dt>
          <dd>
            <span>{{ composition.d3fault }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.composition.arrange')">Arrange</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Arrange.' + composition.arrange)">{{ composition.arrange }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.composition.strategy')">Strategy</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Strategy.' + composition.strategy)">{{ composition.strategy }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.composition.defence')">Defence</span>
          </dt>
          <dd>
            <span>{{ composition.defence }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.composition.shortPass')">Short Pass</span>
          </dt>
          <dd>
            <span>{{ composition.shortPass }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.composition.violence')">Violence</span>
          </dt>
          <dd>
            <span>{{ composition.violence }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.composition.capitan')">Capitan</span>
          </dt>
          <dd>
            <div v-if="composition.capitan">
              <router-link :to="{ name: 'PlayerView', params: { playerId: composition.capitan.id } }">{{
                composition.capitan.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.composition.team')">Team</span>
          </dt>
          <dd>
            <div v-if="composition.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: composition.team.id } }">{{ composition.team.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="composition.id"
          :to="{ name: 'CompositionEdit', params: { compositionId: composition.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./composition-details.component.ts"></script>
