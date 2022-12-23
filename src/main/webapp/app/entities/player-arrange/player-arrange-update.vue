<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.playerArrange.home.createOrEditLabel"
          data-cy="PlayerArrangeCreateUpdateHeading"
          v-text="$t('proFootballClubApp.playerArrange.home.createOrEditLabel')"
        >
          Create or edit a PlayerArrange
        </h2>
        <div>
          <div class="form-group" v-if="playerArrange.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="playerArrange.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.playerArrange.post')" for="player-arrange-post">Post</label>
            <select
              class="form-control"
              name="post"
              :class="{ valid: !$v.playerArrange.post.$invalid, invalid: $v.playerArrange.post.$invalid }"
              v-model="$v.playerArrange.post.$model"
              id="player-arrange-post"
              data-cy="post"
            >
              <option v-for="post in postValues" :key="post" v-bind:value="post" v-bind:label="$t('proFootballClubApp.Post.' + post)">
                {{ post }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.playerArrange.player')" for="player-arrange-player"
              >Player</label
            >
            <select class="form-control" id="player-arrange-player" data-cy="player" name="player" v-model="playerArrange.player">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="playerArrange.player && playerOption.id === playerArrange.player.id ? playerArrange.player : playerOption"
                v-for="playerOption in players"
                :key="playerOption.id"
              >
                {{ playerOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.playerArrange.composition')" for="player-arrange-composition"
              >Composition</label
            >
            <select
              class="form-control"
              id="player-arrange-composition"
              data-cy="composition"
              name="composition"
              v-model="playerArrange.composition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  playerArrange.composition && compositionOption.id === playerArrange.composition.id
                    ? playerArrange.composition
                    : compositionOption
                "
                v-for="compositionOption in compositions"
                :key="compositionOption.id"
              >
                {{ compositionOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.playerArrange.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./player-arrange-update.component.ts"></script>
